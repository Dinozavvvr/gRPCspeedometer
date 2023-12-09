package ru.itis.masternode.api.dto;

import lombok.Getter;

@Getter
public class StartupConfigurationDto {

    private int activeThreadsCount;
    private int startingDelayMillis;
    private int queryDepth;

}
