package ru.itis.masternode.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itis.masternode.model.enums.RequestMethod;
import ru.itis.workernode.emumeration.FlowType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCase {

    private UUID id = UUID.randomUUID();

    private Long createdAt = System.currentTimeMillis();

    private int threadsCount;

    private int requestDepth;

    private int workTime;

    private RequestMethod requestMethod;

    private FlowType flowType;

    private int requestBodySize;

    private TestCaseState state;

    private StatisticsSummary restStatistics;

    private StatisticsSummary grpcStatistics;

}
