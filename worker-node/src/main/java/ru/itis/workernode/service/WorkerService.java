package ru.itis.workernode.service;

import reactor.core.publisher.Mono;
import ru.itis.workernode.model.RequestConfig;
import ru.itis.workernode.model.WorkerConfigInfo;

public interface WorkerService {

    Mono<WorkerConfigInfo> testPostData(WorkerConfigInfo workerConfigInfo);

    Mono<WorkerConfigInfo> testGetData(RequestConfig requestConfig);
}
