package ru.itis.masternode.model;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatisticsSummary {

    private final List<StatisticsPerSecond> statisticsPerSeconds;

}