package ru.itis.masternode.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.itis.masternode.controller.dto.TestCaseDto;
import ru.itis.masternode.model.TestCase;
import ru.itis.masternode.model.TestConfig;

@Slf4j
@Service
@RequiredArgsConstructor
public class GRpcSpeedometerAppService {

    private final TestCaseManager testCaseManager;
    private final TestCaseService testCaseService;
    private final ReportsService reportsService;

    public UUID scheduleTestCase(TestConfig testConfig) {
        return testCaseManager.schedule(testConfig);
    }

    public void stopTestCase(UUID testCaseId) {
        testCaseManager.stop(testCaseId);
    }

    public List<TestCaseDto> getAllTestCases() {
        return testCaseManager.getTestCases();
    }

    public TestCase getTestCase(UUID testCaseId) {
        return testCaseService.getTestCase(testCaseId);
    }

    public ResponseEntity<byte[]> gerReport(UUID testCaseId) {
        TestCase testCase = testCaseService.getTestCase(testCaseId);
        return reportsService.downloadExcel(testCase);
    }

}
