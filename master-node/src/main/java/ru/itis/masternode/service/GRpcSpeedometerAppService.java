package ru.itis.masternode.service;

import ru.itis.masternode.api.dto.TestConfig;

public interface GRpcSpeedometerAppService {

    void scheduleTest(TestConfig testConfig);

    void stopTest();

}
