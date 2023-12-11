package ru.itis.masternode.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class EnvironmentManager {

    private DockerClient dockerClient;
    private final List<String> nodes = new ArrayList<>();
    private String directNode;

    @PostConstruct
    private void init() {
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        DockerHttpClient dockerHttpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .build();

        this.dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);

    }

    public String requireNodesChain(int nodeChainSize) throws InterruptedException {
        if (nodeChainSize < nodes.size()) {
            // todo: поднять еще нод
            for (int i = nodeChainSize; i < nodes.size(); i++) {
                if (!Thread.currentThread().isInterrupted()) {
                    directNode = createNode();
                    nodes.add(directNode);
                } else {
                    throw new InterruptedException();
                }
            }
        }

        return directNode;
    }

    private String createNode() {
        String imageName = "worker-node:1.0.0";

        Ports portBindings = new Ports();
        portBindings.bind(ExposedPort.tcp(80), Ports.Binding.bindPort(8080));

        HostConfig hostConfig = HostConfig.newHostConfig()
                .withPortBindings(portBindings)
                .withNetworkMode("mynet");

        String[] envVariables = {"HOST=localhost"};

        CreateContainerResponse container = dockerClient.createContainerCmd(imageName)
                .withEnv(envVariables)
                .withHostConfig(hostConfig)
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();
        NetworkSettings networkSettings = dockerClient.inspectContainerCmd(container.getId()).exec().getNetworkSettings();
        ContainerNetwork mynetNetwork = networkSettings.getNetworks().get("mynet");
        String internalIpAddress = mynetNetwork.getIpAddress();

        log.info("Container started successfully. Internal IP: " + internalIpAddress);
        return internalIpAddress;
    }

}
