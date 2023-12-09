package ru.itis.masternode.state;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class AppState {

    private final AtomicReference<State> state = new AtomicReference<>(State.WAITING);

    public State getState() {
        return state.get();
    }

    public void initiating() {
        this.state.compareAndSet(State.WAITING, State.INITIATING);
    }

    public void preparing() {
        this.state.compareAndSet(State.INITIATING, State.PREPARING);
    }

    public void starting() {
        this.state.compareAndSet(State.PREPARING, State.STARTING);
    }

    public void working() {
        this.state.compareAndSet(State.STARTING, State.WORKING);
    }

    public void finishing() {
        this.state.compareAndSet(State.WORKING, State.FINISHING);
    }

    public void waiting() {
        this.state.compareAndSet(State.FINISHING, State.WAITING);
    }

    public enum State {
        WAITING,
        INITIATING,
        PREPARING,
        STARTING,
        WORKING,
        FINISHING
    }

}
