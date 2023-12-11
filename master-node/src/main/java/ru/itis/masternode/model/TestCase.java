package ru.itis.masternode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.itis.masternode.model.enums.FlowType;
import ru.itis.masternode.model.enums.RequestMethod;

import java.util.UUID;

@Getter
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

}
