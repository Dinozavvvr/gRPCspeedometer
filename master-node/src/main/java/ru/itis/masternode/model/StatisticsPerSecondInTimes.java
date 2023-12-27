package ru.itis.masternode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsPerSecondInTimes {

    private long second;

    private double requestsPerSecond;

    private double averageRequestsPerSecond;

    private double totalRequestCount;

    private String testCaseId;

}
