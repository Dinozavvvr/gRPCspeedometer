package ru.itis.masternode.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.masternode.model.TestConfig;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GRpcSpeedometerAppService {

    private final TestCaseManager testCaseManager;

    public UUID scheduleTest(TestConfig testConfig) {
        return testCaseManager.schedule(testConfig);
    }

    public void stopTest(UUID testCaseId) {
        testCaseManager.stop(testCaseId);
    }

}
