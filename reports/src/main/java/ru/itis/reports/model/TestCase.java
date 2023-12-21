package ru.itis.reports.model;

import lombok.*;
import ru.itis.reports.model.enums.FlowType;
import ru.itis.reports.model.enums.RequestMethod;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestCase {

    private UUID id = UUID.randomUUID();

    private int threadsCount;
    private int requestDepth;
    private int workTime;
    private RequestMethod requestMethod;
    private FlowType flowType;
    private int requestBodySize;

    private StatisticsSummary restStatistics;
    private StatisticsSummary grpcStatistics;

    public TestCase(UUID uuid, int i, int i1, int i2, RequestMethod requestMethod, FlowType flowType, int i3) {
    }
}
