package ru.itis.masternode.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.masternode.model.TestConfig;
import ru.itis.masternode.service.GRpcSpeedometerAppService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grpc-speedometer/tests")
public class GRpcSpeedometerAppController {

    private final GRpcSpeedometerAppService gRpcSpeedometerAppService;

    @PostMapping("/start")
    public ResponseEntity<?> startTestCase(@RequestBody TestConfig startupConfiguration) {
        return ResponseEntity.ok(gRpcSpeedometerAppService.scheduleTestCase(startupConfiguration));
    }

    @GetMapping
    public ResponseEntity<?> getAllTestCases() {
        return ResponseEntity.ok(gRpcSpeedometerAppService.getAllTestCases());
    }

    @GetMapping("/{testCaseId}")
    public ResponseEntity<?> getTestCase(@PathVariable UUID testCaseId) {
        return ResponseEntity.ok(gRpcSpeedometerAppService.getTestCase(testCaseId));
    }

    @GetMapping("/{testCaseId}/report")
    public ResponseEntity<byte[]> downloadTestCaseReport(@PathVariable UUID testCaseId) {
        return gRpcSpeedometerAppService.gerReport(testCaseId);
    }

    @PostMapping("/{testCaseId}/stop")
    public ResponseEntity<?> stop(@PathVariable UUID testCaseId) {
        gRpcSpeedometerAppService.stopTestCase(testCaseId);

        return ResponseEntity.ok().build();
    }

}
