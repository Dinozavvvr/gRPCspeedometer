package ru.itis.masternode.grpc;

import com.google.common.util.concurrent.SettableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.itis.workernode.emumeration.FlowType;
import ru.itis.workernode.grpc.RequestConfig;
import ru.itis.workernode.grpc.WorkerConfigInfo;
import ru.itis.workernode.grpc.WorkerGrpcServiceGrpc;
import ru.itis.workernode.model.ExampleData;

@Service
public class MasterGrpcService {

    private WorkerGrpcServiceGrpc.WorkerGrpcServiceStub workerGrpcServiceStub;

    private ManagedChannel channel;

    public void changeGrpcServerAddress(String newAddress) {
        if (channel != null) {
            channel.shutdown();
        }

        ManagedChannel channel = ManagedChannelBuilder.forTarget(newAddress)
                .usePlaintext()
                .keepAliveTime(120, TimeUnit.SECONDS)
                .build();
        this.channel = channel;

        this.workerGrpcServiceStub = WorkerGrpcServiceGrpc.newStub(channel);
    }

    public Mono<?> testGetData(ru.itis.workernode.grpc.WorkerConfigInfo workerConfigInfo) {
        return Mono.create(sink -> {
            workerGrpcServiceStub.testGetData(workerConfigInfo, new StreamObserver<>() {
                @Override
                public void onNext(ru.itis.workernode.grpc.WorkerConfigInfo response) {
                    sink.success(response);
                }

                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }

                @Override
                public void onCompleted() {
                }
            });
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<?> testPostData(WorkerConfigInfo workerConfigInfo) {
        return Mono.create(sink -> {
            workerGrpcServiceStub.testPostData(workerConfigInfo, new StreamObserver<>() {
                @Override
                public void onNext(ru.itis.workernode.grpc.WorkerConfigInfo response) {
                    sink.success(response);
                }

                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }

                @Override
                public void onCompleted() {
                }
            });
        }).subscribeOn(Schedulers.boundedElastic());
    }

}
