package ru.itis.workernode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.itis.workernode.model.RequestConfig;
import ru.itis.workernode.model.WorkerConfigInfo;
import ru.itis.workernode.service.WorkerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
public class WorkerController {

    private final WorkerService workerService;

    @PostMapping(value = "/rest")
    public Mono<WorkerConfigInfo> testPostData(@RequestBody WorkerConfigInfo workerConfigInfo) {
        return workerService.testPostData(workerConfigInfo);
    }

    @GetMapping(value = "/rest")
    public Mono<WorkerConfigInfo> testGetData(RequestConfig requestConfig) {
        return workerService.testGetData(requestConfig);
    }
}
