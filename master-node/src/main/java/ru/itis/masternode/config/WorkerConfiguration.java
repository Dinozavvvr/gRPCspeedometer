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

    public Integer exposedPort = 8080;

    // user specific configuration
    public Integer targetPort = 8888;

    public String host = "localhost";

}
