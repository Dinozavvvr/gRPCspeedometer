package ru.itis.masternode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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
import ru.itis.masternode.model.TestCaseStatistics;
import ru.itis.masternode.model.enums.RequestMethod;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestCaseRunner {

    private TestCase currentTestCase;

    private final EnvironmentManager environmentManager;

    private RunnerState runnerState = RunnerState.WAITING_TASK;

    private TestCaseRunnerThread testCaseRunnerThread;

    private final Consumer<TestCase> stoppingCallback = testCase -> {
        synchronized (this) {
            this.currentTestCase = null;
            this.testCaseRunnerThread = null;
            this.changeState(RunnerState.WAITING_TASK);
            this.notifyAll();
        }
    };

    private final Consumer<TestCase> successCallback = testCase -> {
        synchronized (this) {
            this.currentTestCase = null;
            this.testCaseRunnerThread = null;
            this.changeState(RunnerState.WAITING_TASK);
            this.notifyAll();
        }
    };

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
                testCase, stoppingCallback, successCallback, stoppingCallback);
        new Thread(testCaseRunnerThread).start();
        log.info("[{}] TestCaseRunnerThread for test case started", testCase.getId());
    }

    public synchronized void stopTestCase() {
        if (currentTestCase == null) {
            throw new IllegalArgumentException("No active test case to stop");
        }
        this.testCaseRunnerThread.stop();
    }

    public synchronized boolean isAvailable() {
        return runnerState.equals(RunnerState.WAITING_TASK);
    }

    private synchronized void changeState(RunnerState runnerState) {
        this.runnerState = runnerState;
    }

    @RequiredArgsConstructor
    public static class TestCaseRunnerThread implements Runnable {

        private volatile boolean interrupted = false;

        private final TestCaseRunner testCaseRunner;

        private final TestCase testCase;

        private final Consumer<TestCase> stoppingCallback;

        private final Consumer<TestCase> successCallback;

        private final Consumer<TestCase> errorCallback;

        @Override
        public void run() {
            try {
                log.info("[{}] Test case execution started", testCase.getId());
                runInternal();
                successCallback.accept(testCase);
                log.info("[{}] Test case execution finished", testCase.getId());
            } catch (Exception e) {
                log.info("[{}] Test case execution aborted", testCase.getId());
                stoppingCallback.accept(testCase);
            }
        }

        public void runInternal() {
            String workerHost = createContainer();

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
            TestCaseStatistics testCaseStatistics = new TestCaseStatistics(testCase);

            List<Thread> publisherThreads = new ArrayList<>();
            long delayPerThread = 10;

            long threadStopTime = System.currentTimeMillis() +      // current time
                    testCase.getWorkTime() * 1000L                  // work time millis
                    + delayPerThread * testCase.getThreadsCount();  // delay of last thread

            for (int i = 0; i < testCase.getThreadsCount(); i++) {
                AbstractRequestPublisher abstractRequestPublisher;

                if (testCase.getRequestMethod().equals(RequestMethod.GET)) {
                    abstractRequestPublisher = new RestApiGetRequestPublisher(
                            testCase, workerHost, testCaseStatistics, threadStopTime
                    );
                } else {
                    abstractRequestPublisher = new RestApiPostRequestPublisher(
                            testCase, workerHost, testCaseStatistics, threadStopTime);
                }
                Thread thread = new Thread(abstractRequestPublisher);
                publisherThreads.add(thread);
            }

            SequenceThreadExecutor sequenceThreadExecutor = new SequenceThreadExecutor(
                    publisherThreads);
            try {
                sequenceThreadExecutor.execute(delayPerThread);
                testCaseStatistics.start(delayPerThread * publisherThreads.size());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return testCaseStatistics.stop();
            }

            for (Thread publisherThread : publisherThreads) {
                try {
                    publisherThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return testCaseStatistics.stop();
                }
            }

            return testCaseStatistics.stop();
        }

        public synchronized void stop() {
            this.interrupted = true;
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

        protected volatile boolean interrupted = false;

        protected final TestCaseStatistics testCaseStatistics;

        protected final Long autoStopAtTime;

        protected final TestCase testCase;

        protected synchronized boolean isInterrupted() {
            return interrupted;
        }

        protected synchronized void stop() {
            this.interrupted = true;
        }

    }


    public static class RestApiPostRequestPublisher extends AbstractRequestPublisher {

        private final WebClient webClient;

        public RestApiPostRequestPublisher(TestCase testCase, String workerHost,
                TestCaseStatistics testCaseStatistics, Long autoStopAtTime) {
            super(testCaseStatistics, autoStopAtTime, testCase);
            this.webClient = WebClient.builder()
                    .baseUrl(workerHost)
                    .build();
        }

        @Override
        public void run() {
            while (!isInterrupted() && autoStopAtTime > System.currentTimeMillis()) {
                webClient.post().uri("/worker/rest")
                        .bodyValue(new Object())
                        .retrieve()
                        .bodyToMono(Object.class)
                        .doOnNext(response -> {
                        })
                        .subscribe();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }

    public static class RestApiGetRequestPublisher extends AbstractRequestPublisher {

        private final WebClient webClient;

        private final String getUri;

        public RestApiGetRequestPublisher(TestCase testCase, String workerHost,
                TestCaseStatistics testCaseStatistics, Long autoStopAtTime) {
            super(testCaseStatistics, autoStopAtTime, testCase);

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
            while (!isInterrupted() && autoStopAtTime > System.currentTimeMillis()) {
                webClient.get().uri(getUri)
                        .retrieve()
                        .bodyToMono(Object.class)
                        .doOnSuccess(response -> testCaseStatistics.registerSuccessRequest())
                        .subscribe();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }

    private enum RunnerState {
        WAITING_TASK,
        PREPARING
    }

    private static String buildGetUri(TestCase testCase) {
        return String.format("/worker/rest?depthLevel=%s&dataSize=%s&flowType=%s",
                testCase.getRequestDepth(),
                testCase.getRequestBodySize(),
                testCase.getFlowType()
        );
    }

}