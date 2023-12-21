package ru.itis.masternode.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import ru.itis.masternode.model.StatisticsSummary;
import ru.itis.masternode.model.TestCase;
import ru.itis.masternode.model.TestCaseState;
import ru.itis.masternode.model.TestCaseStatistics;
import ru.itis.masternode.model.enums.RequestMethod;
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

    private RunnerState runnerState = RunnerState.WAITING_TASK;

    private TestCaseRunnerThread testCaseRunnerThread;

    private final Consumer<TestCase> stoppingCallback = testCase -> {
        synchronized (this) {
            testCase.setState(TestCaseState.ABORTED);
            this.currentTestCase = null;
            this.testCaseRunnerThread = null;
            this.changeState(RunnerState.WAITING_TASK);
            this.notifyAll();
        }
    };

    public Consumer<TestCase> successCallback() {
        return testCase -> {
            synchronized (this) {
                testCase.setState(TestCaseState.FINISHED);
                testCaseService.updateTestCase(testCase);
                this.currentTestCase = null;
                this.testCaseRunnerThread = null;
                this.changeState(RunnerState.WAITING_TASK);
                this.notifyAll();
            }
        };
    }

    public void run(TestCase testCase) throws IllegalStateException {
        synchronized (this) {
            if (!isAvailable()) {
                throw new IllegalStateException("Scheduler is not available to accept test cases");
            }
        }

        this.currentTestCase = testCase;
        changeState(RunnerState.PREPARING);

        log.info("[{}] Starting TestCaseRunnerThread for test ... ", testCase.getId());
        this.testCaseRunnerThread = new TestCaseRunnerThread(this,
                testCase, testCaseService, stoppingCallback, successCallback(), stoppingCallback);
        new Thread(testCaseRunnerThread).start();
        log.info("[{}] TestCaseRunnerThread for test case started", testCase.getId());
    }

    public synchronized void stopTestCase() {
        if (currentTestCase == null) {
            throw new IllegalArgumentException("No active test case to stop");
        }
        this.testCaseRunnerThread.stopRunning(true);
        this.testCaseRunnerThread.interrupt();
    }

    public synchronized boolean isAvailable() {
        return runnerState.equals(RunnerState.WAITING_TASK);
    }

    private synchronized void changeState(RunnerState runnerState) {
        this.runnerState = runnerState;
    }

    @RequiredArgsConstructor
    public static class TestCaseRunnerThread extends Thread {

        private final TestCaseRunner testCaseRunner;

        private final TestCase testCase;

        private final TestCaseService testCaseService;

        private final Consumer<TestCase> stoppingCallback;

        private final Consumer<TestCase> successCallback;

        private final Consumer<TestCase> errorCallback;

        private List<Thread> runningThreads;

        @Override
        public void run() {
            Thread.currentThread().setPriority(MAX_PRIORITY);
            try {
                log.info("[{}] Test case execution started", testCase.getId());
                runInternal();
                successCallback.accept(testCase);
                log.info("[{}] Test case execution finished", testCase.getId());
            } catch (Exception e) {
                log.info("[{}] Test case execution aborted", testCase.getId());
                errorCallback.accept(testCase);
            }
        }

        public void runInternal() {
            String workerHost = createContainer();

            testCase.setState(TestCaseState.RUNNING);
            testCaseService.saveTestCase(testCase);
            testCase.setRestStatistics(startPublisherRestThreads(workerHost));
        }

        private String createContainer() {
            try {
                return testCaseRunner.environmentManager.requireNodesChain(
                        testCase.getRequestDepth());
            } catch (InterruptedException e) {
                log.info("Interrupted during container creating process");
                Thread.currentThread().interrupt();
                return null;
            }
        }

        private StatisticsSummary startPublisherRestThreads(String workerHost) {
            TestCaseStatistics testCaseStatistics = new TestCaseStatistics(testCase, testCase.getWorkTime());

            List<Thread> publisherThreads = new ArrayList<>();
            long delayPerThread = 50;

            for (int i = 0; i < testCase.getThreadsCount(); i++) {
                AbstractRequestPublisher abstractRequestPublisher;

                if (testCase.getRequestMethod().equals(RequestMethod.GET)) {
                    abstractRequestPublisher = new RestApiGetRequestPublisher(
                            testCase, workerHost, testCaseStatistics
                    );
                } else {
                    abstractRequestPublisher = new RestApiPostRequestPublisher(
                            testCase, workerHost, testCaseStatistics);
                }
                Thread thread = new Thread(abstractRequestPublisher);
                publisherThreads.add(thread);
            }

            SequenceThreadExecutor sequenceThreadExecutor = new SequenceThreadExecutor(
                    publisherThreads);

            this.runningThreads = publisherThreads;
            StatisticsSummary statisticsSummary;
            try {
                sequenceThreadExecutor.execute(delayPerThread);
                testCaseStatistics.start();
                Thread.sleep(testCase.getWorkTime() * 1000L + 1000);
                statisticsSummary = testCaseStatistics.get();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            stopRunning(false);
            return statisticsSummary;
        }

        public synchronized void stopRunning(Boolean interrupted) {
            runningThreads.forEach(Thread::interrupt);

            if (interrupted) {
                this.stoppingCallback.accept(testCase);
            }
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