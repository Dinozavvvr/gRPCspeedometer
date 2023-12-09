package ru.itis.masternode.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.masternode.api.dto.TestConfig;
import ru.itis.masternode.model.Test;
import ru.itis.masternode.scheduler.service.Scheduler;
import ru.itis.masternode.service.GRpcSpeedometerAppService;
import ru.itis.masternode.service.TaskManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class GRpcSpeedometerAppServiceImpl implements GRpcSpeedometerAppService {

    private final TaskManager taskManager;

    @Override
    public void scheduleTest(TestConfig testConfig) {
        taskManager.schedule(testConfig);
    }

    @Override
    public void stopTest() {

    }

}
