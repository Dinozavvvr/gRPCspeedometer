package ru.itis.masternode.service;

import lombok.RequiredArgsConstructor;
import ru.itis.masternode.api.dto.TestConfig;
import ru.itis.masternode.model.Test;
import ru.itis.masternode.scheduler.service.Scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


@RequiredArgsConstructor
public class TaskManager implements Runnable {

    private final BlockingQueue<Test> waitingTests = new LinkedBlockingQueue<>(3);
    private final Scheduler scheduler;
    private Test currentTest;

    public void schedule(TestConfig testConfig) {
        try {
            Test test = new Test(testConfig);
            waitingTests.add(test);
        } catch (IllegalStateException e) {
            System.out.println("No available space. Queue is full");
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Test test = waitingTests.take();
                while (!scheduler.isAvailable()) {
                    synchronized (scheduler) {
                        scheduler.wait();
                    }
                }

                scheduler.schedule(test);
                setCurrentTest(test);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void setCurrentTest(Test test) {
        synchronized (this) {
            currentTest = test;
        }
        System.out.println("Active test changed: " + test);
    }

    public Test getCurrentTest() {
        return currentTest;
    }

}
