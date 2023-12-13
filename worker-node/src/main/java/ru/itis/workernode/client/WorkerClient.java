package ru.itis.workernode.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.itis.workernode.exception.model.PostDataException;
import ru.itis.workernode.model.RequestConfig;
import ru.itis.workernode.model.WorkerConfigInfo;

@Component
@Slf4j
public class WorkerClient {

    private final WebClient webClient;

    public WorkerClient(WebClient.Builder webClientBuilder, @Value("${worker.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<WorkerConfigInfo> postDataToWorker(WorkerConfigInfo workerConfigInfo) {
        return webClient.post().uri("/worker/rest")
                .bodyValue(workerConfigInfo)
                .retrieve()
                .bodyToMono(WorkerConfigInfo.class)
                .doOnSubscribe(subscription -> log.info("Post data to another worker"))
                .doOnError(err -> {
                            log.error(String.format("Error Post data to another worker: %s", err.getMessage()));
                            throw new PostDataException(err.getMessage());
                        }
                );
    }

    public Mono<WorkerConfigInfo> getDataToWorker(RequestConfig requestConfig) {
        return webClient.get().uri(buildGetUri(requestConfig))
                .retrieve()
                .bodyToMono(WorkerConfigInfo.class)
                .doOnSubscribe(subscription -> log.info("Post data to another worker"))
                .doOnError(err -> {
                    log.error(String.format("Error Get data from another worker: %s", err.getMessage()));
                            throw new PostDataException(err.getMessage());
                        }
                );
    }

    private String buildGetUri(RequestConfig requestConfig) {
        return String.format("/worker/rest?depthLevel=%s&dataSize=%s&flowType=%s",
                requestConfig.getDepthLevel(),
                requestConfig.getDataSize(),
                requestConfig.getFlowType()
        );
    }
}
