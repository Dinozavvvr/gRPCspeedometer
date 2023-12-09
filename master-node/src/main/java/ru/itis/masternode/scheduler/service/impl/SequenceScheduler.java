package ru.itis.masternode.scheduler.service.impl;

import lombok.SneakyThrows;
import ru.itis.masternode.model.Test;
import ru.itis.masternode.scheduler.service.Scheduler;


public class SequenceScheduler implements Scheduler {

    private Test currentTest;
    private State state = State.WAITING_TASK;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            changeState(State.WAITING_TASK);

            if (currentTest == null) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            prepare();
        }
    }

    @SneakyThrows
    private void prepare() {
        System.out.println("Preparing test: " + currentTest);
        Thread.sleep(5000);
        System.out.println("Test prepared: " + currentTest);

        currentTest = null;
        synchronized (this) {
            notifyAll();
        }
    }

    private synchronized void changeState(State state) {
        this.state = state;
    }

    @Override
    public synchronized void schedule(Test test) throws IllegalStateException {
        if (!isAvailable()) {
            throw new IllegalStateException("Scheduler is not available");
        }

        changeState(State.PREPARING);
        currentTest = test;
        notifyAll();
    }

    @Override
    public synchronized boolean isAvailable() {
        return state.equals(State.WAITING_TASK);
    }

    private enum State {
        WAITING_TASK,
        PREPARING
    }

}