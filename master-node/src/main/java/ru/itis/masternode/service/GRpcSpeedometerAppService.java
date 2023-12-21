package ru.itis.masternode.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.masternode.controller.dto.TestCaseDto;
import ru.itis.masternode.model.StatisticsSummary;
import ru.itis.masternode.model.TestCase;
import ru.itis.masternode.model.TestConfig;

@Slf4j
@Service
@RequiredArgsConstructor
public class GRpcSpeedometerAppService {

    private final TestCaseManager testCaseManager;
    private final TestCaseService testCaseService;

    public UUID scheduleTest(TestConfig testConfig) {
        return testCaseManager.schedule(testConfig);
    }

    public void stopTest(UUID testCaseId) {
        testCaseManager.stop(testCaseId);
    }

    public List<TestCaseDto> getAllTests() {
        return testCaseManager.getTestCases();
    }

    public TestCase getTest(UUID testCaseId) {
        return testCaseService.getTestCase(testCaseId);
    }

}
