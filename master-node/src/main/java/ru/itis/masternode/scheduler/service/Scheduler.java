package ru.itis.masternode.scheduler.service;

import ru.itis.masternode.model.Test;

public interface Scheduler extends Runnable {

    void schedule(Test test) throws IllegalStateException;

    boolean isAvailable();

}
