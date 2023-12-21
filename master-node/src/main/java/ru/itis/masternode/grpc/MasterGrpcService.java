package ru.itis.masternode.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.itis.workernode.emumeration.FlowType;
import ru.itis.workernode.grpc.RequestConfig;
import ru.itis.workernode.grpc.WorkerConfigInfo;
import ru.itis.workernode.grpc.WorkerGrpcServiceGrpc;
import ru.itis.workernode.model.ExampleData;

import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class MasterGrpcServiceImpl {

    @GrpcClient("master-client")
    private WorkerGrpcServiceGrpc.WorkerGrpcServiceBlockingStub workerGrpcServiceBlockingStub;

    public ru.itis.workernode.model.WorkerConfigInfo testPostData(ru.itis.workernode.model.WorkerConfigInfo workerConfigInfo) {
        WorkerConfigInfo result = workerGrpcServiceBlockingStub.testPostData(WorkerConfigInfo.newBuilder()
                .setConfig(RequestConfig.newBuilder()
                        .setDepthLevel(workerConfigInfo.getConfig().getDepthLevel())
                        .setDataSize(workerConfigInfo.getConfig().getDataSize())
                        .setFlowType(FlowType.WATERFALL.equals(workerConfigInfo.getConfig().getFlowType()) ?
                                ru.itis.workernode.grpc.FlowType.WATERFALL :
                                ru.itis.workernode.grpc.FlowType.BATCH)
                        .build())
                .addAllData(Collections.emptyList())
                .build());
        return ru.itis.workernode.model.WorkerConfigInfo.builder()
                .config(ru.itis.workernode.model.RequestConfig.builder()
                        .depthLevel(result.getConfig().getDepthLevel())
                        .dataSize(result.getConfig().getDataSize())
                        .flowType(result.getConfig().getFlowType().equals(ru.itis.workernode.grpc.FlowType.WATERFALL) ?
                                FlowType.WATERFALL :
                                FlowType.BATCH)
                        .build())
                .data(result.getDataList().stream().map(data -> ExampleData.builder()
                        .id(data.getId())
                        .name(data.getName())
                        .lastName(data.getLastName())
                        .age(data.getAge())
                        .date(LocalDate.parse(data.getDate()))
                        .height(data.getHeight())
                        .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public ru.itis.workernode.model.WorkerConfigInfo testGetData(ru.itis.workernode.model.RequestConfig requestConfig) {
        WorkerConfigInfo result = workerGrpcServiceBlockingStub.testGetData(WorkerConfigInfo.newBuilder()
                .setConfig(RequestConfig.newBuilder()
                        .setDepthLevel(requestConfig.getDepthLevel())
                        .setDataSize(requestConfig.getDataSize())
                        .setFlowType(FlowType.WATERFALL.equals(requestConfig.getFlowType()) ?
                                ru.itis.workernode.grpc.FlowType.WATERFALL :
                                ru.itis.workernode.grpc.FlowType.BATCH)
                        .build())
                .addAllData(Collections.emptyList())
                .build());
        return ru.itis.workernode.model.WorkerConfigInfo.builder()
                .config(ru.itis.workernode.model.RequestConfig.builder()
                        .depthLevel(result.getConfig().getDepthLevel())
                        .dataSize(result.getConfig().getDataSize())
                        .flowType(result.getConfig().getFlowType().equals(ru.itis.workernode.grpc.FlowType.WATERFALL) ?
                                FlowType.WATERFALL :
                                FlowType.BATCH)
                        .build())
                .data(result.getDataList().stream().map(data -> ExampleData.builder()
                                .id(data.getId())
                                .name(data.getName())
                                .lastName(data.getLastName())
                                .age(data.getAge())
                                .date(LocalDate.parse(data.getDate()))
                                .height(data.getHeight())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
