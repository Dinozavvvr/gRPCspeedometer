package ru.itis.workernode.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkerConfigInfo {

    private RequestConfig config;

    private List<ExampleData> data;
}
