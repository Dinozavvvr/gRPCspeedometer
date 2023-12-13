package ru.itis.workernode.service.impl;

import lombok.RequiredArgsConstructor;
import org.instancio.Instancio;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.itis.workernode.client.WorkerClient;
import ru.itis.workernode.emumeration.FlowType;
import ru.itis.workernode.model.ExampleData;
import ru.itis.workernode.model.RequestConfig;
import ru.itis.workernode.model.WorkerConfigInfo;
import ru.itis.workernode.service.WorkerService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerClient workerClient;

    @Override
    public Mono<WorkerConfigInfo> testPostData(WorkerConfigInfo workerConfigInfo) {
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
                                data.getData().add(Instancio.create(ExampleData.class));
                            }
                        }
                        return data;
                    });
        }
        List<ExampleData> data = new ArrayList<>();
        for (int i = 0; i < requestConfig.getDataSize(); i++) {
            data.add(Instancio.create(ExampleData.class));
        }
        return Mono.just(
                WorkerConfigInfo.builder()
                        .config(requestConfig)
                        .data(data)
                        .build()
        );
    }
}
