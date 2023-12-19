package ru.itis.masternode.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ContainerNetwork;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.NetworkSettings;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.itis.masternode.config.WorkerConfiguration;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvironmentManager {

    private final List<WorkerNode> nodes = new ArrayList<>();

    private final WorkerConfiguration workerConfiguration;

    private Integer nextAvailablePort = 10000;

    private DockerClient dockerClient;

    private WorkerNode nodeToConnect;

    @PostConstruct
    private void init() {
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .build();
        DockerHttpClient dockerHttpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .build();

        this.dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
        dockerClient.createNetworkCmd().withName(workerConfiguration.network).exec();
    }

    public String requireNodesChain(int nodeChainSize) throws InterruptedException {
        if (nodeChainSize > nodes.size()) {
            createNodes(nodeChainSize - nodes.size());
            Thread.sleep(5000); // starting spring on node
        }

        return "http://127.0.0.1:" + nodeToConnect.targetPort;
    }

    private void createNodes(int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            if (!Thread.currentThread().isInterrupted()) {
                WorkerNode workerNode = WorkerNode.builder()
                        .exposedPort(workerConfiguration.exposedPort)
                        .targetPort(++nextAvailablePort)
                        .build();

                if (nodeToConnect != null) {
                    workerNode.setNextNodeIp(nodeToConnect.internalIp);
                    workerNode.setNextNodePort(nodeToConnect.exposedPort);
                }

                String internalIp = createNode(workerNode);
                workerNode.setInternalIp(internalIp);

                nodes.add(workerNode);
                nodeToConnect = workerNode;
            } else {
                throw new InterruptedException();
            }
        }
    }

    private String createNode(WorkerNode workerNode) {
        Ports portBindings = new Ports();
        portBindings.bind(ExposedPort.tcp(workerNode.exposedPort),
                Ports.Binding.bindPort(workerNode.targetPort));

        HostConfig hostConfig = HostConfig.newHostConfig()
                .withPortBindings(portBindings)
                .withNetworkMode(workerConfiguration.network);

        CreateContainerResponse container = dockerClient.createContainerCmd(
                        workerConfiguration.image)
                .withEnv(List.of("HOST=http://" + workerNode.nextNodeIp + ":"
                        + workerNode.nextNodePort))
                .withHostConfig(hostConfig)
                .exec();


        dockerClient.startContainerCmd(container.getId()).exec();
        NetworkSettings networkSettings = dockerClient.inspectContainerCmd(container.getId()).exec()
                .getNetworkSettings();
        ContainerNetwork network = networkSettings.getNetworks().get(workerConfiguration.network);
        String internalIpAddress = network.getIpAddress();

        workerNode.setId(container.getId());
        log.info("Container started successfully. Internal IP: {}", internalIpAddress);
        return internalIpAddress;
    }

    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class WorkerNode {

        private String id;

        private Integer targetPort;

        private Integer exposedPort;

        private String internalIp;

        private String nextNodeIp;

        private Integer nextNodePort;

    }

    @PreDestroy
    private void destroy() {
        deleteWorkersContainers();
        deleteWorkersNetwork();
    }


    private void deleteWorkersNetwork() {
        dockerClient.removeNetworkCmd(workerConfiguration.network).exec();
        log.info("Network {}, successfully removed", workerConfiguration.network);
    }

    private void deleteWorkersContainers() {
        nodes.forEach(workerNode -> {
            dockerClient.stopContainerCmd(workerNode.id).exec();
            dockerClient.removeContainerCmd(workerNode.id).exec();
            log.info("Container removed successfully. Container id: {}", workerNode.id);
        });
    }


}
