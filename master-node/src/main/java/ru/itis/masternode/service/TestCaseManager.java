package ru.itis.masternode.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.itis.masternode.controller.dto.TestCaseDto;
import ru.itis.masternode.exception.TestCaseQueueLimitViolationException;
import ru.itis.masternode.model.TestCase;
import ru.itis.masternode.model.TestCaseState;
import ru.itis.masternode.model.TestConfig;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestCaseManager {

    private final BlockingQueue<TestCase> testsCaseQueue = new LinkedBlockingQueue<>(3);

    private final TestCaseRunner testCaseRunner;

    private final TestCaseService testCaseService;

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
                testsCaseQueue.add(testCase);

                testCase.setState(TestCaseState.WAITING);
                testCaseService.saveTestCase(testCase);
            }
            return testCase.getId();
        } catch (IllegalStateException e) {
            throw new TestCaseQueueLimitViolationException("No available space. Queue is full");
        }
    }

    public synchronized void stop(UUID testCaseId) {
        TestCaseState testCaseState = testCaseService.getTestCase(testCaseId).getState();

        switch (testCaseState) {
            case RUNNING: testCaseRunner.stopTestCase();
                break;
            case WAITING:
                    testsCaseQueue.stream().filter(testCase -> testCase.getId() == testCaseId)
                            .forEach(testsCaseQueue::remove);
        }
    }

    public List<TestCaseDto> getTestCases() {
        return testCaseService.getAllTestCases().stream()
                .map(testCase -> TestCaseDto.builder()
                .id(testCase.getId())
                .state(testCase.getState())
                .build()).collect(Collectors.toList());
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

                    testCaseManager.testCaseRunner.run(testCase);
                    testCase.setState(TestCaseState.RUNNING);
                    testCaseManager.testCaseService.updateTestCase(testCase);
                    log.info("[{}] Test case started ", testCase.getId());
                } catch (InterruptedException e) {
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

    public static TestCase createTestCase(TestConfig testConfig) {
        return new TestCase(UUID.randomUUID(),
                testConfig.getThreadsCount(), testConfig.getRequestDepth(),
                testConfig.getWorkTime(), testConfig.getRequestMethod(),
                testConfig.getFlowType(), testConfig.getRequestBodySize(),
                TestCaseState.CREATED,
                null, null
        );
    }

}
