package ru.itis.masternode.model;

import lombok.Data;
import ru.itis.masternode.model.enums.RequestMethod;
import ru.itis.workernode.emumeration.FlowType;

@Data
public class TestConfig {

    private int threadsCount;

    private int requestDepth;

    private int workTime;

    private RequestMethod requestMethod;

    private FlowType flowType;

    private int requestBodySize;

}
