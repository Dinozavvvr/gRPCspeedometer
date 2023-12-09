package ru.itis.masternode.api.dto;

import lombok.Getter;

@Getter
public class TestConfig {

    private int activeThreadsCount;
    private int startingDelayMillis;
    private int queryDepth;

}
