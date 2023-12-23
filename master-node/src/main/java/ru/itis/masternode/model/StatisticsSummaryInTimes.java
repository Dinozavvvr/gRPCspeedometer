package ru.itis.masternode.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class StatisticsSummaryInTimes {

    private final List<StatisticsPerSecondInTimes> statisticsPerSeconds;

}