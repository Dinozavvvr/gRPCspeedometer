package ru.itis.masternode.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.itis.masternode.model.TestCase;
import ru.itis.masternode.model.enums.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

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

    @SneakyThrows
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
                testCase, null, null, stoppingCallback);
        new Thread(testCaseRunnerThread).start();
        log.info("[{}] TestCaseRunnerThread for test case started", testCase.getId());
    }

    public synchronized void stopTestCase() {
        if (currentTestCase == null) throw new IllegalArgumentException("No active test case to stop");
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
                Thread.sleep(100000);
                log.info("[{}] Test case execution finished", testCase.getId());
            } catch (Exception e) {
                log.info("[{}] Test case execution aborted", testCase.getId());
                stoppingCallback.accept(testCase);
            }
        }

        private void runInternal() {
            // prepare environment
            try {
                String host = testCaseRunner.environmentManager.requireNodesChain(testCase.getRequestDepth());
            } catch (InterruptedException e) {
                log.info("interrupted during container creating process");
            }

            // rest
            List<Thread> publisherThreads = new ArrayList<>();
            for (int i = 0; i < testCase.getThreadsCount(); i++) {
                AbstractRequestPublisher abstractRequestPublisher;
                if (testCase.getRequestMethod().equals(RequestMethod.GET)) {
                    abstractRequestPublisher = new RestApiGetRequestPublisher();
                } else {
                    abstractRequestPublisher = new RestApiPostRequestPublisher();
                }
                publisherThreads.add(new Thread(abstractRequestPublisher));
            }
            try {
                new SequenceThreadExecutor(publisherThreads).execute(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            for (Thread publisherThread : publisherThreads) {
                try {
                    publisherThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // grpc
        }

        public synchronized void stop() {
            this.interrupted = true;
        }

    }

    @RequiredArgsConstructor
    public static class SequenceThreadExecutor {

        private final List<Thread> threads;
        private volatile boolean isExecuted = false;

        public void execute(int delayMillis) throws InterruptedException {
            if (isExecuted) throw new IllegalStateException("SequenceThreadExecutor already used");

            for (Thread thread : threads) {
                thread.start();
                Thread.sleep(delayMillis);
            }

            isExecuted = true;
        }

    }

    public static abstract class AbstractRequestPublisher implements Runnable {

        protected volatile boolean interrupted = false;

        protected synchronized boolean isInterrupted() {
            return interrupted;
        }

        protected synchronized void stop() {
            this.interrupted = true;
        }

    }


    public static class RestApiPostRequestPublisher extends AbstractRequestPublisher {
        private final WebClient webClient = WebClient.builder().build();

        @Override
        public void run() {
            while (!isInterrupted()) {
                webClient.post().uri("/worker/rest")
                        .bodyValue(new Object())
                        .retrieve()
                        .bodyToMono(Object.class)
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
        private final WebClient webClient = WebClient.builder().build();

        @Override
        public void run() {
            while (!isInterrupted()) {
                webClient.get().uri("/worker/rest")
                        .retrieve()
                        .bodyToMono(Object.class)
                        .subscribe();
                try {
                    Thread.sleep(1);
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

}