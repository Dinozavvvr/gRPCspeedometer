package ru.itis.masternode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.masternode.api.GRpcSpeedometerApp;
import ru.itis.masternode.api.dto.TestConfig;
import ru.itis.masternode.service.GRpcSpeedometerAppService;

@RestController
@RequiredArgsConstructor
public class GRpcSpeedometerAppController implements GRpcSpeedometerApp {

    private final GRpcSpeedometerAppService gRpcSpeedometerAppService;

    @Override
    public ResponseEntity<?> startTest(TestConfig startupConfiguration) {
        gRpcSpeedometerAppService.scheduleTest(startupConfiguration);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> stop() {
        gRpcSpeedometerAppService.stopTest();

        return ResponseEntity.ok().build();
    }
}
