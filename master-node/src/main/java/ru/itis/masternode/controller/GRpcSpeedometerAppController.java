package ru.itis.masternode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.masternode.model.TestConfig;
import ru.itis.masternode.service.GRpcSpeedometerAppService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grpc-speedometer")
public class GRpcSpeedometerAppController {

    private final GRpcSpeedometerAppService gRpcSpeedometerAppService;

    @PostMapping("/start")
    public ResponseEntity<?> startTest(TestConfig startupConfiguration) {
        return ResponseEntity.ok(gRpcSpeedometerAppService.scheduleTest(startupConfiguration));
    }

    @PostMapping("/stop/{testCaseId}")
    public ResponseEntity<?> stop(@PathVariable UUID testCaseId) {
        gRpcSpeedometerAppService.stopTest(testCaseId);

        return ResponseEntity.ok().build();
    }
}
