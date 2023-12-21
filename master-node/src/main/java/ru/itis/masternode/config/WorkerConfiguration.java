package ru.itis.masternode.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "worker")
public class WorkerConfiguration {

    public String image = "worker:latest";

    public String network = "workers-net";

    public Integer exposedRestPort = 8080;

    // user specific configuration
    public Integer targetRestPort = 8888;

    public Integer exposedGrpcPort = 9090;

    // user specific configuration
    public Integer targetGrpcPort = 9999;

    public String host = "localhost";

}
