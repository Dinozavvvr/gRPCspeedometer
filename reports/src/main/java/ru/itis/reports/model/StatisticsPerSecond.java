package ru.itis.reports.model;

import java.util.UUID;

public record StatisticsPerSecond(long second, long requestsPerSecond,
                                  long averageRequestsPerSecond, long totalRequestCount,
                                  UUID testCaseId) {

}