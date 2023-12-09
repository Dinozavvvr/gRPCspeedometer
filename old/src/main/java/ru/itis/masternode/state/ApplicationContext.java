package ru.itis.masternode.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class ApplicationContext {

    @Getter
    private final AppState appState;
    private final AtomicReference<StartupState> desiredState = new AtomicReference<>();
    private final AtomicReference<StartupState> currentState = new AtomicReference<>();

    public StartupState getDesiredState() {
        return desiredState.get();
    }

    public void setDesiredState(StartupState desiredState) {
        this.desiredState.compareAndSet(null, desiredState);
    }

    public StartupState getCurrentState() {
        return currentState.get();
    }

    public void setCurrentState(StartupState desiredState) {
        this.currentState.compareAndSet(null, desiredState);
    }

    public void clear() {
        this.desiredState.set(null);
        this.currentState.set(null);
    }

}
