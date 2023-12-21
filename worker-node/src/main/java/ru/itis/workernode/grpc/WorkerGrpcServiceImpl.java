package ru.itis.workernode.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.itis.workernode.exception.model.PostDataException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@GrpcService
public class WorkerGrpcServiceImpl extends WorkerGrpcServiceGrpc.WorkerGrpcServiceImplBase {

    private static final ExampleData exampleData = ExampleData.newBuilder()
            .setId(1)
            .setName("name")
            .setLastName("lastName")
            .setAge(22)
            .setDate(String.valueOf(LocalDate.now()))
            .setHeight(5.5)
            .build();

    private static final String MANY_DATA_OBJECTS = "Объектов должно быть не меньше, чем depthLevel * dataSize";

    @GrpcClient("grpc-client")
    private WorkerGrpcServiceGrpc.WorkerGrpcServiceBlockingStub workerGrpcServiceStub;

    @Override
    public void testPostData(WorkerConfigInfo workerConfigInfo, StreamObserver<WorkerConfigInfo> responseObserver) {
        if (workerConfigInfo.getDataList().size() <
                workerConfigInfo.getConfig().getDataSize() * workerConfigInfo.getConfig().getDepthLevel()) {
            throw new PostDataException(MANY_DATA_OBJECTS);
        }
        if (workerConfigInfo.getConfig().getDepthLevel() == 0) {
            responseObserver.onNext(workerConfigInfo);
            responseObserver.onCompleted();
        } else {
            List<ExampleData> data = new ArrayList<>(workerConfigInfo.getDataList());
            if (FlowType.WATERFALL.equals(workerConfigInfo.getConfig().getFlowType())) {
                for (int i = 1; i <= workerConfigInfo.getConfig().getDataSize(); i++) {
                    data.remove(data.size() - 1);
                }
            }
            WorkerConfigInfo newWorkerConfigInfo = WorkerConfigInfo.newBuilder()
                    .setConfig(RequestConfig.newBuilder()
                            .setDepthLevel(workerConfigInfo.getConfig().getDepthLevel() - 1)
                            .setDataSize(workerConfigInfo.getConfig().getDataSize())
                            .setFlowType(workerConfigInfo.getConfig().getFlowType())
                            .build())
                    .addAllData(data)
                    .build();
            WorkerConfigInfo result = workerGrpcServiceStub.testPostData(newWorkerConfigInfo);
            responseObserver.onNext(result);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void testGetData(WorkerConfigInfo workerConfigInfo, StreamObserver<WorkerConfigInfo> responseObserver) {
        List<ExampleData> data = new ArrayList<>();
        for (int i = 0; i < workerConfigInfo.getConfig().getDataSize(); i++) {
            data.add(exampleData);
        }
        data.addAll(workerConfigInfo.getDataList());
        WorkerConfigInfo newWorkerConfigInfo = WorkerConfigInfo.newBuilder()
                .setConfig(RequestConfig.newBuilder()
                        .setDepthLevel(workerConfigInfo.getConfig().getDepthLevel() - 1)
                        .setDataSize(workerConfigInfo.getConfig().getDataSize())
                        .setFlowType(workerConfigInfo.getConfig().getFlowType())
                        .build())
                .addAllData(data)
                .build();
        if (newWorkerConfigInfo.getConfig().getDepthLevel() == 0) {
            responseObserver.onNext(newWorkerConfigInfo);
            responseObserver.onCompleted();
        } else {
            WorkerConfigInfo result = workerGrpcServiceStub.testGetData(newWorkerConfigInfo);
            responseObserver.onNext(result);
            responseObserver.onCompleted();
        }
    }
}
