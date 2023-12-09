package ru.itis.masternode.state;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.itis.masternode.api.dto.StartupConfigurationDto;

import java.util.concurrent.atomic.AtomicInteger;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StartupState {

    private AtomicInteger activeThreadsCount = new AtomicInteger(0);

    private AtomicInteger queryDepth = new AtomicInteger(0);

    private StartupState(int activeThreadsCount, int queryDepth) {
        this.activeThreadsCount = new AtomicInteger(activeThreadsCount);
        this.queryDepth = new AtomicInteger(queryDepth);
    }


    public void plusActiveThread() {
        activeThreadsCount.incrementAndGet();
    }

    public void plusQueryDepth() {
        queryDepth.incrementAndGet();
    }


    public static StartupState create(StartupConfigurationDto startupConfiguration) {
        return new StartupState(startupConfiguration.getActiveThreadsCount(), startupConfiguration.getQueryDepth());
    }

    public static StartupState create() {
        return new StartupState();
    }

}
