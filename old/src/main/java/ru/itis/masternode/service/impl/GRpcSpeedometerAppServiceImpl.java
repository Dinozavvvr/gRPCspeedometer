package ru.itis.masternode.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import ru.itis.masternode.api.dto.StartupConfigurationDto;
import ru.itis.masternode.scheduler.service.SchedulerService;
import ru.itis.masternode.service.GRpcSpeedometerAppService;
import ru.itis.masternode.state.AppState;
import ru.itis.masternode.state.ApplicationContext;
import ru.itis.masternode.state.StartupState;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
public class GRpcSpeedometerAppServiceImpl implements GRpcSpeedometerAppService {

    private final SchedulerService schedulerService;
    private final ApplicationContext applicationContext;

    @Override
    public void startTest(StartupConfigurationDto startupConfiguration) {
        if (isBusy()) return; // todo: response - test already running

        applicationContext.setDesiredState(StartupState.create(startupConfiguration));

        schedulerService.prepareEnvironment()
                .flatMap(schedulerState -> schedulerService.start())
                .doOnNext(System.out::println)
                .doOnTerminate(applicationContext::clear)
                .subscribe();
    }

    @Override
    public void stopTest() {
        if (!isBusy()) return; // todo: response - no active test

        applicationContext.clear();
    }

    private synchronized boolean isBusy() {
        return !applicationContext.getAppState().getState().equals(AppState.State.WAITING);
    }

}
