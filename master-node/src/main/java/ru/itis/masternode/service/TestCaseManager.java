package ru.itis.masternode.service;

import jakarta.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.itis.masternode.model.TestCase;
import ru.itis.masternode.model.TestCaseState;
import ru.itis.masternode.model.TestConfig;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestCaseManager {

    private final BlockingQueue<TestCase> testsCaseQueue = new LinkedBlockingQueue<>(3);

    private final ConcurrentHashMap<UUID, TestCaseState> testCaseStates = new ConcurrentHashMap<>();

    private final TestCaseRunner testCaseRunner;

    @PostConstruct
    public void init() {
        log.info("Starting TestCaseManagerQueueListenerThread ... ");
        new Thread(testCaseManagerQueueListenerThread).start();
        log.info("TestCaseManagerQueueListenerThread started");
    }

    private final TestCaseManagerQueueListenerThread testCaseManagerQueueListenerThread =
            new TestCaseManagerQueueListenerThread(this);

    public UUID schedule(TestConfig testConfig) {
        TestCase testCase = TestCaseManager.createTestCase(testConfig);

        try {
            synchronized (this) {
                changeTestCaseState(testCase, TestCaseState.CREATED);
                testsCaseQueue.add(testCase);
                changeTestCaseState(testCase, TestCaseState.WAITING);
            }
        } catch (IllegalStateException e) {
            log.warn("No available space. Queue is full");
            changeTestCaseState(testCase, TestCaseState.ABORTED);
        }

        return testCase.getId();
    }

    public synchronized void stop(UUID testCaseId) {
        TestCaseState testCaseState = testCaseStates.get(testCaseId);

        switch (testCaseState) {
            case PREPARING -> testCaseRunner.stopTestCase();
            case PENDING -> testCaseManagerQueueListenerThread.stopPending();
            case WAITING ->
                    testsCaseQueue.stream().filter(testCase -> testCase.getId() == testCaseId)
                            .forEach(testsCaseQueue::remove);
        }
    }

    private synchronized void changeTestCaseState(TestCase testCase, TestCaseState state) {
        testCaseStates.put(testCase.getId(), state);
    }

    @RequiredArgsConstructor
    public static class TestCaseManagerQueueListenerThread implements Runnable {

        private final TestCaseManager testCaseManager;

        private Thread testCasePendingThread;

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    testCasePendingThread = null;
                    TestCase testCase = testCaseManager.testsCaseQueue.take();

                    var pendingThread = new TestCaseManagerPendingThread(
                            testCaseManager.testCaseRunner, testCase);
                    var futureTask = new FutureTask<>(pendingThread);

                    testCasePendingThread = new Thread(futureTask);

                    testCasePendingThread.start();
                    testCaseManager.changeTestCaseState(testCase, TestCaseState.PENDING);
                    log.info("[{}] Pending test case ... ", testCase.getId());

                    if (futureTask.get()) {
                        log.info("[{}] Test case accepted by runner ", testCase.getId());
                        testCaseManager.changeTestCaseState(testCase, TestCaseState.PREPARING);
                    } else {
                        log.error("[{}] Test case aborted ", testCase);
                        testCaseManager.changeTestCaseState(testCase, TestCaseState.ABORTED);
                    }

                } catch (InterruptedException | ExecutionException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private synchronized void stopPending() {
            if (testCasePendingThread != null) {
                testCasePendingThread.interrupt();
            }
        }

    }

    @RequiredArgsConstructor
    public static class TestCaseManagerPendingThread implements Callable<Boolean> {

        private final TestCaseRunner testCaseRunner;

        private final TestCase testCase;

        @Override
        public Boolean call() {
            try {
                while (!testCaseRunner.isAvailable()) {
                    synchronized (testCaseRunner) {
                        testCaseRunner.wait();
                    }
                }
                testCaseRunner.run(testCase);
                return true;
            } catch (InterruptedException e) {
                log.info("[{}] Canceling pending test ", testCase);
                return false;
            }
        }

    }

    public static TestCase createTestCase(TestConfig testConfig) {
        return new TestCase(UUID.randomUUID(),
                testConfig.getThreadsCount(), testConfig.getRequestDepth(),
                testConfig.getWorkTime(), testConfig.getRequestMethod(),
                testConfig.getFlowType(), testConfig.getRequestBodySize(),
                null, null
        );
    }

}
