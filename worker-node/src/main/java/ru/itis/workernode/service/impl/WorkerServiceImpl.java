package ru.itis.workernode.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.itis.workernode.client.WorkerClient;
import ru.itis.workernode.emumeration.FlowType;
import ru.itis.workernode.exception.model.PostDataException;
import ru.itis.workernode.model.ExampleData;
import ru.itis.workernode.model.RequestConfig;
import ru.itis.workernode.model.WorkerConfigInfo;
import ru.itis.workernode.service.WorkerService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private static final ExampleData exampleData = ExampleData.builder()
            .id(1)
            .name("name")
            .lastName("lastName")
            .age(22)
            .date(LocalDate.now())
            .height(5.5)
            .build();

    private static final String MANY_DATA_OBJECTS = "Объектов должно быть не меньше, чем depthLevel * dataSize";

    private final WorkerClient workerClient;

    @Override
    public Mono<WorkerConfigInfo> testPostData(WorkerConfigInfo workerConfigInfo) {
        if (workerConfigInfo.getData().size() <
                workerConfigInfo.getConfig().getDataSize() * workerConfigInfo.getConfig().getDepthLevel()) {
            throw new PostDataException(MANY_DATA_OBJECTS);
        }
        if (workerConfigInfo.getConfig().getDepthLevel() == 0) {
            return Mono.just(workerConfigInfo);
        } else {
            if (FlowType.WATERFALL.equals(workerConfigInfo.getConfig().getFlowType())) {
                for (int i = 1; i <= workerConfigInfo.getConfig().getDataSize(); i++) {
                    workerConfigInfo.getData().remove(workerConfigInfo.getData().size() - 1);
                }
            }
            workerConfigInfo.getConfig().setDepthLevel(workerConfigInfo.getConfig().getDepthLevel() - 1);
            return workerClient.postDataToWorker(workerConfigInfo);
        }
    }

    @Override
    public Mono<WorkerConfigInfo> testGetData(RequestConfig requestConfig) {
        requestConfig.setDepthLevel(requestConfig.getDepthLevel() - 1);
        if (requestConfig.getDepthLevel() > 0) {
            return workerClient.getDataToWorker(requestConfig)
                    .map(data -> {
                        if (FlowType.WATERFALL.equals(data.getConfig().getFlowType())) {
                            for (int i = 0; i < data.getConfig().getDepthLevel(); i++) {
                                data.getData().add(exampleData);
                            }
                        }
                        return data;
                    });
        }
        List<ExampleData> data = new ArrayList<>();
        for (int i = 0; i < requestConfig.getDataSize(); i++) {
            data.add(exampleData);
        }
        return Mono.just(
                WorkerConfigInfo.builder()
                        .config(requestConfig)
                        .data(data)
                        .build()
        );
    }
}
