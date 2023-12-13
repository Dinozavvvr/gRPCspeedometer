package ru.itis.workernode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.workernode.emumeration.FlowType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestConfig {

    private Integer depthLevel;

    private Integer dataSize;

    private FlowType flowType;
}
