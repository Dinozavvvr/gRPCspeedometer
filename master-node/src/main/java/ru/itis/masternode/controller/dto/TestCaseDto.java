package ru.itis.masternode.controller.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.masternode.model.TestCaseState;
import ru.itis.masternode.model.enums.RequestMethod;
import ru.itis.workernode.emumeration.FlowType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseDto {

    private UUID id;

    private int threadsCount;

    private int requestDepth;

    private int workTime;

    private RequestMethod requestMethod;

    private FlowType flowType;

    private int requestBodySize;

    private TestCaseState state;

}
