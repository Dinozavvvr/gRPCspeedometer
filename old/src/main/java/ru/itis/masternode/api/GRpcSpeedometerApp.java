package ru.itis.masternode.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.masternode.api.dto.StartupConfigurationDto;

@RequestMapping("/grpc-speedometer")
public interface GRpcSpeedometerApp {

    @PostMapping("/start")
    ResponseEntity<?> startTest(StartupConfigurationDto startupConfiguration);

    @PostMapping("/stop")
    ResponseEntity<?> stop();

}
