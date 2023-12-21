package ru.itis.masternode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.masternode.grpc.MasterGrpcServiceImpl;
import ru.itis.workernode.model.RequestConfig;
import ru.itis.workernode.model.WorkerConfigInfo;

@RestController
@RequiredArgsConstructor
public class GrpcController {

    private final MasterGrpcServiceImpl masterGrpcService;

    @PostMapping("test/grpc")
    public WorkerConfigInfo testPostData(@RequestBody WorkerConfigInfo workerConfigInfo) {
        return masterGrpcService.testPostData(workerConfigInfo);
    }

    @GetMapping("/test/grpc")
    public WorkerConfigInfo testGetData(RequestConfig requestConfig) {
        return masterGrpcService.testGetData(requestConfig);
    }
}
