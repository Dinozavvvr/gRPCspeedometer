package ru.itis.masternode.model;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StatisticsSummary {

    private final List<StatisticsPerSecond> statisticsPerSeconds;

}