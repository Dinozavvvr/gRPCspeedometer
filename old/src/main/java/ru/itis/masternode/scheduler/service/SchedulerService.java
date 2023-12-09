package ru.itis.masternode.scheduler.service;

import reactor.core.publisher.Mono;
import ru.itis.masternode.scheduler.SchedulerState;
import ru.itis.masternode.state.StartupState;

import java.util.concurrent.Future;

public interface SchedulerService {

    Mono<StartupState> prepareEnvironment();

    Mono<StartupState> start();

}
