package ru.itis.masternode.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itis.masternode.model.enums.RequestMethod;
import ru.itis.workernode.emumeration.FlowType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
