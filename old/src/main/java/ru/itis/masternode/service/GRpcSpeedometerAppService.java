package ru.itis.masternode.service;

import ru.itis.masternode.api.dto.StartupConfigurationDto;

public interface GRpcSpeedometerAppService {

    void startTest(StartupConfigurationDto startupConfiguration);

    void stopTest();

}
