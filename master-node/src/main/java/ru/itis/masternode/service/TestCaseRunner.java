package ru.itis.masternode.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import ru.itis.masternode.grpc.MasterGrpcService;
import ru.itis.masternode.model.TestCase;
import ru.itis.masternode.model.TestCaseState;
import ru.itis.masternode.model.enums.RequestMethod;
import ru.itis.workernode.emumeration.FlowType;
import ru.itis.workernode.model.ExampleData;
import ru.itis.workernode.model.RequestConfig;
import ru.itis.workernode.model.WorkerConfigInfo;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestCaseRunner {

    private TestCase currentTestCase;

    private final EnvironmentManager environmentManager;

    private final TestCaseService testCaseService;

    private final MasterGrpcService masterGrpcService;

    private RunnerState runnerState = RunnerState.WAITING_TASK;

    private TestCaseRunnerThread testCaseRunnerThread;

    private final Consumer<TestCase> stoppingCallback() {
        return testCase -> {
            synchronized (this) {
                testCase.setState(TestCaseState.ABORTED);
                testCaseService.updateTestCase(testCase);
                this.currentTestCase = null;
                this.changeState(RunnerState.WAITING_TASK);
                this.notifyAll();
            }
        };
    }

    public Consumer<TestCase> successCallback() {
        return testCase -> {
            synchronized (this) {
                testCase.setState(TestCaseState.FINISHED);
                testCaseService.updateTestCase(testCase);
                this.currentTestCase = null;
                this.changeState(RunnerState.WAITING_TASK);
                this.notifyAll();
            }
        };
    }

    public synchronized void run(TestCase testCase)
            throws IllegalStateException, InterruptedException {
        if (!isAvailable()) {
            this.wait();
        }
        this.currentTestCase = testCase;
        changeState(RunnerState.PREPARING);

        log.info("[{}] Starting TestCaseRunnerThread for test ... ", testCase.getId());
        this.testCaseRunnerThread = new TestCaseRunnerThread();
        this.testCaseRunnerThread.start();
        log.info("[{}] TestCaseRunnerThread for test case started", testCase.getId());
    }

    public synchronized void stopTestCase() {
        if (currentTestCase == null) {
            throw new IllegalArgumentException("No active test case to stop");
        }
        this.testCaseRunnerThread.interrupt();
    }

    public synchronized boolean isAvailable() {
        return runnerState.equals(RunnerState.WAITING_TASK);
    }

    private synchronized void changeState(RunnerState runnerState) {
        this.runnerState = runnerState;
    }

    @RequiredArgsConstructor
    public class TestCaseRunnerThread extends Thread {

        private List<Thread> runningThreads;

        @Override
        public void run() {
            Thread.currentThread().setPriority(MAX_PRIORITY);
            runInternal();
        }

        public void runInternal() {
            try {
                log.info("[{}] Test case REST execution started", currentTestCase.getId());
                TestCaseStatistics testCaseRestStatistics = new TestCaseStatistics(
                        currentTestCase, currentTestCase.getWorkTime(), "REST");
                startPublisherRestThreads(testCaseRestStatistics);

                log.info("[{}] Test case REST execution finished", currentTestCase.getId());
                log.info("[{}] Test case gRPC execution started", currentTestCase.getId());

                TestCaseStatistics testCaseGrpcStatistics = new TestCaseStatistics(
                        currentTestCase, currentTestCase.getWorkTime(), "GRPC");

                startPublisherGrpcThreads(testCaseGrpcStatistics);
                log.info("[{}] Test case gRPC execution finished", currentTestCase.getId());
                successCallback().accept(currentTestCase);
            } catch (InterruptedException e) {
                log.info("[{}] Test case execution aborted", currentTestCase.getId());
                stoppingCallback().accept(currentTestCase);
            } finally {
                System.gc();
            }
        }

        private void startPublisherRestThreads(TestCaseStatistics testCaseStatistics) throws InterruptedException {
            String workerHost = environmentManager
                    .requireNodesChainRest(currentTestCase.getRequestDepth());

            List<Thread> publisherThreads = new ArrayList<>();
            long delayPerThread = 50;

            for (int i = 0; i < currentTestCase.getThreadsCount(); i++) {
                AbstractRequestPublisher abstractRequestPublisher;

                if (currentTestCase.getRequestMethod().equals(RequestMethod.GET)) {
                    abstractRequestPublisher = new RestApiGetRequestPublisher(
                            currentTestCase, workerHost, testCaseStatistics
                    );
                } else {
                    abstractRequestPublisher = new RestApiPostRequestPublisher(
                            currentTestCase, workerHost, testCaseStatistics);
                }
                Thread thread = new Thread(abstractRequestPublisher);
                publisherThreads.add(thread);
            }

            SequenceThreadExecutor sequenceThreadExecutor = new SequenceThreadExecutor(
                    publisherThreads);

            this.runningThreads = publisherThreads;
            try {
                sequenceThreadExecutor.execute(delayPerThread);

                testCaseStatistics.start();
                Thread.sleep(currentTestCase.getWorkTime() * 1000L + 1000);
                testCaseStatistics.stop();

            } finally {
                testCaseStatistics.stop();
                stopRunning();
                currentTestCase.setRestStatistics(testCaseStatistics.get());
            }
        }

        private void startPublisherGrpcThreads(TestCaseStatistics testCaseStatistics)
                throws InterruptedException {
            String workerHost = environmentManager
                    .requireNodesChainGrpc(currentTestCase.getRequestDepth());
            masterGrpcService.changeGrpcServerAddress(workerHost);

            List<Thread> publisherThreads = new ArrayList<>();
            long delayPerThread = 50;

            for (int i = 0; i < currentTestCase.getThreadsCount(); i++) {
                AbstractRequestPublisher abstractRequestPublisher;

                if (currentTestCase.getRequestMethod().equals(RequestMethod.GET)) {
                    abstractRequestPublisher = new GrpcGetRequestPublisher(
                            currentTestCase, masterGrpcService, testCaseStatistics
                    );
                } else {
                    abstractRequestPublisher = new GrpcPostRequestPublisher(
                            currentTestCase, masterGrpcService, testCaseStatistics);
                }
                Thread thread = new Thread(abstractRequestPublisher);
                publisherThreads.add(thread);
            }

            SequenceThreadExecutor sequenceThreadExecutor = new SequenceThreadExecutor(
                    publisherThreads);

            this.runningThreads = publisherThreads;
            try {
                sequenceThreadExecutor.execute(delayPerThread);

                testCaseStatistics.start();
                Thread.sleep(currentTestCase.getWorkTime() * 1000L + 1000);
            } finally {
                testCaseStatistics.stop();
                stopRunning();
                currentTestCase.setGrpcStatistics(testCaseStatistics.get());
            }
        }

        public synchronized void stopRunning() {
            runningThreads.forEach(Thread::interrupt);
        }

    }

    @RequiredArgsConstructor
    public static class SequenceThreadExecutor {

        private final List<Thread> threads;

        private volatile boolean isExecuted = false;

        public void execute(long delayMillis) throws InterruptedException {
            if (isExecuted) {
                throw new IllegalStateException("SequenceThreadExecutor already used");
            }

            for (Thread thread : threads) {
                thread.start();
                Thread.sleep(delayMillis);
            }

            isExecuted = true;
        }

    }

    @RequiredArgsConstructor
    public static abstract class AbstractRequestPublisher implements Runnable {

        protected final TestCaseStatistics testCaseStatistics;

        protected final TestCase testCase;

    }


    public static class GrpcGetRequestPublisher extends AbstractRequestPublisher {

        private final MasterGrpcService masterGrpcService;

        private final ru.itis.workernode.grpc.WorkerConfigInfo workerConfigInfo;

        public GrpcGetRequestPublisher(TestCase testCase, MasterGrpcService masterGrpcService,
                TestCaseStatistics testCaseStatistics) {
            super(testCaseStatistics, testCase);

            this.masterGrpcService = masterGrpcService;
            RequestConfig requestConfig = RequestConfig.builder()
                    .depthLevel(testCase.getRequestDepth() - 1)
                    .flowType(testCase.getFlowType())
                    .dataSize(testCase.getRequestBodySize())
                    .build();

            this.workerConfigInfo = ru.itis.workernode.grpc.WorkerConfigInfo.newBuilder()
                    .setConfig(ru.itis.workernode.grpc.RequestConfig.newBuilder()
                            .setDepthLevel(requestConfig.getDepthLevel())
                            .setDataSize(requestConfig.getDataSize())
                            .setFlowType(
                                    FlowType.WATERFALL.equals(requestConfig.getFlowType()) ?
                                            ru.itis.workernode.grpc.FlowType.WATERFALL :
                                            ru.itis.workernode.grpc.FlowType.BATCH)
                            .build())
                    .addAllData(Collections.emptyList())
                    .build();
        }

        @Override
        public void run() {
            log.info("thread started");
            while (!Thread.currentThread().isInterrupted()) {
                masterGrpcService.testGetData(workerConfigInfo)
                        .doOnNext(res -> testCaseStatistics.registerSuccessRequest())
                        .subscribe();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            log.info("thread finished");
        }

    }

    public static class GrpcPostRequestPublisher extends AbstractRequestPublisher {

        private final ru.itis.workernode.grpc.ExampleData exampleData =
                ru.itis.workernode.grpc.ExampleData.newBuilder()
                .setId(1)
                .setName("name")
                .setLastName("lastName")
                .setAge(22)
                .setDate(LocalDate.now().toString())
                .setHeight(5.5)
                .build();
        private final MasterGrpcService masterGrpcService;

        private final ru.itis.workernode.grpc.WorkerConfigInfo workerConfigInfo;

        public GrpcPostRequestPublisher(TestCase testCase, MasterGrpcService masterGrpcService,
                TestCaseStatistics testCaseStatistics) {
            super(testCaseStatistics, testCase);

            this.masterGrpcService = masterGrpcService;
            RequestConfig requestConfig = RequestConfig.builder()
                    .depthLevel(testCase.getRequestDepth() - 1)
                    .flowType(testCase.getFlowType())
                    .dataSize(testCase.getRequestBodySize())
                    .build();

            List<ru.itis.workernode.grpc.ExampleData> data = new ArrayList<>();
            for (int i = 0; i < testCase.getRequestDepth() * testCase.getRequestBodySize(); i++) {
                data.add(exampleData);
            }

            this.workerConfigInfo = ru.itis.workernode.grpc.WorkerConfigInfo.newBuilder()
                    .setConfig(ru.itis.workernode.grpc.RequestConfig.newBuilder()
                            .setDepthLevel(requestConfig.getDepthLevel())
                            .setDataSize(requestConfig.getDataSize())
                            .setFlowType(
                                    FlowType.WATERFALL.equals(requestConfig.getFlowType()) ?
                                            ru.itis.workernode.grpc.FlowType.WATERFALL :
                                            ru.itis.workernode.grpc.FlowType.BATCH)
                            .build())
                    .addAllData(data)
                    .build();
        }

        @Override
        public void run() {
            log.info("thread started");
            while (!Thread.currentThread().isInterrupted()) {
                masterGrpcService.testPostData(workerConfigInfo)
                        .doOnNext(res -> testCaseStatistics.registerSuccessRequest())
                        .subscribe();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            log.info("thread finished");
        }

    }


    public static class RestApiPostRequestPublisher extends AbstractRequestPublisher {

        private static final ExampleData exampleData = ExampleData.builder()
                .id(1)
                .name("name")
                .lastName("lastName")
                .age(22)
                .date(LocalDate.now())
                .height(5.5)
                .build();

        private final WebClient webClient;

        private final WorkerConfigInfo workerConfigInfo;

        public RestApiPostRequestPublisher(TestCase testCase, String workerHost,
                TestCaseStatistics testCaseStatistics) {
            super(testCaseStatistics, testCase);

            List<ExampleData> data = new ArrayList<>();
            for (int i = 0; i < testCase.getRequestDepth() * testCase.getRequestBodySize(); i++) {
                data.add(exampleData);
            }

            this.workerConfigInfo = WorkerConfigInfo.builder()
                    .config(RequestConfig.builder()
                            .dataSize(testCase.getRequestBodySize())
                            .flowType(testCase.getFlowType())
                            .depthLevel(testCase.getRequestDepth() - 1)
                            .build())
                    .data(data)
                    .build();

            ConnectionProvider client = ConnectionProvider.builder("custom")
                    .pendingAcquireMaxCount(3000).build();

            this.webClient = WebClient.builder()
                    .baseUrl(workerHost)
                    .clientConnector(new ReactorClientHttpConnector(HttpClient.create(client)))
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
        }

        @Override
        public void run() {
            log.info("thread started");
            while (!Thread.currentThread().isInterrupted()) {
                webClient.post().uri("/worker/rest")
                        .bodyValue(workerConfigInfo)
                        .retrieve()
                        .bodyToMono(Object.class)
                        .doOnNext(response -> testCaseStatistics.registerSuccessRequest())
                        .subscribe();
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            log.info("thread finished");
        }

    }

    public static class RestApiGetRequestPublisher extends AbstractRequestPublisher {

        private final WebClient webClient;

        private final String getUri;

        public RestApiGetRequestPublisher(TestCase testCase, String workerHost,
                TestCaseStatistics testCaseStatistics) {
            super(testCaseStatistics, testCase);

            ConnectionProvider client = ConnectionProvider.builder("custom")
                    .pendingAcquireMaxCount(3000).build();

            this.getUri = buildGetUri(testCase);
            this.webClient = WebClient.builder()
                    .baseUrl(workerHost)
                    .clientConnector(new ReactorClientHttpConnector(HttpClient.create(client)))
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                webClient.get().uri(getUri)
                        .retrieve()
                        .bodyToMono(Object.class)
                        .doOnSuccess(response -> testCaseStatistics.registerSuccessRequest())
                        .subscribe();
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

    }

    private enum RunnerState {
        WAITING_TASK,
        PREPARING,
        RUNNING
    }

    private static String buildGetUri(TestCase testCase) {
        return String.format("/worker/rest?depthLevel=%s&dataSize=%s&flowType=%s",
                testCase.getRequestDepth(),
                testCase.getRequestBodySize(),
                testCase.getFlowType()
        );
    }

}