package ru.itis.masternode.scheduler.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.itis.masternode.scheduler.service.SchedulerService;
import ru.itis.masternode.state.ApplicationContext;
import ru.itis.masternode.state.StartupState;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final ApplicationContext applicationContext;

    @SneakyThrows
    @Override
    public Mono<StartupState> prepareEnvironment() {
        return Mono.defer(() -> {
            applicationContext.setCurrentState(StartupState.create());

            StartupState currentState = applicationContext.getCurrentState();
            currentState.plusActiveThread();
            currentState.plusActiveThread();
            currentState.plusActiveThread();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return Mono.error(e);
            }

            return Mono.just(applicationContext.getCurrentState());
        }).doOnCancel(() -> System.out.println("Preparing stop"));
    }

    @Override
    public Mono<StartupState> start() {
        StartupState currentState = applicationContext.getCurrentState();
        currentState.plusQueryDepth();
        currentState.plusQueryDepth();
        currentState.plusQueryDepth();

        return Mono.just(applicationContext.getCurrentState());
    }

}
