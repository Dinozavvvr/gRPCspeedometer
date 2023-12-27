package ru.itis.masternode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class StatisticsPerSecond {

    private long second;

    private long requestsPerSecond;

    private long averageRequestsPerSecond;

    private long totalRequestCount;

    private String testCaseId;

    private String type;

}