package ru.itis.masternode.model;

import lombok.Data;
import ru.itis.masternode.model.enums.FlowType;
import ru.itis.masternode.model.enums.RequestMethod;

@Data
public class TestConfig {

    private int threadsCount;
    private int requestDepth;
    private int workTime;
    private RequestMethod requestMethod;
    private FlowType flowType;
    private int requestBodySize;

}
