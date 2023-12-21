package ru.itis.reports.model;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StatisticsSummary {

    private final List<StatisticsPerSecond> statisticsPerSeconds;

}