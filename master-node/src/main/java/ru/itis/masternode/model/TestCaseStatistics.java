package ru.itis.masternode.model;

import com.google.firebase.cloud.FirestoreClient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCaseStatistics {

    private final AtomicLong totalRequestsCount = new AtomicLong(0);

    private final AtomicLong requestsPerSecond = new AtomicLong(0);

    private final AtomicLong pastSecondsCount = new AtomicLong(0);

    private final AtomicLong averageRequestsPerSecond = new AtomicLong(0);

    private final ScheduledExecutorService tasksExecutor = Executors.newScheduledThreadPool(1);

    private final TestCase testCase;

    private final AtomicInteger secondsToRun;

    private final String type;

    private volatile boolean isStarted = false;


    private final List<StatisticsPerSecond> statisticsPerSecond = new ArrayList<>();

    public TestCaseStatistics(TestCase testCase, int seconds, String type) {
        this.type = type;
        this.testCase = testCase;
        this.secondsToRun = new AtomicInteger(seconds);
    }

    public void start() {
        if (isStarted) {
            throw new IllegalStateException("TestCaseStatistics already have been started");
        }

        isStarted = true;

        tasksExecutor.scheduleAtFixedRate(new UpdatePeriodicalStatsTask(), 1000, 1000,
                TimeUnit.MILLISECONDS);
    }

    public void registerSuccessRequest() {
        if (isStarted) {
            totalRequestsCount.incrementAndGet();
            requestsPerSecond.incrementAndGet();
        }
    }


    public StatisticsSummary get() {
        return new StatisticsSummary(statisticsPerSecond);
    }

    public void stop() {
        tasksExecutor.shutdownNow();
    }

    @AllArgsConstructor
    private class UpdatePeriodicalStatsTask implements Runnable {

        @Override
        public void run() {
            if (secondsToRun.get() > 0) {
                long pastSeconds = pastSecondsCount.incrementAndGet();
                averageRequestsPerSecond.set((totalRequestsCount.get() / pastSeconds));

                log.info("------------------------");
                log.info("second: {} ", pastSeconds);
                log.info("total req count: {} ", totalRequestsCount.get());
                log.info("req per second: {} ", requestsPerSecond.get());

                StatisticsPerSecond statPerSecond = new StatisticsPerSecond(pastSeconds,
                        requestsPerSecond.get(),
                        averageRequestsPerSecond.get(), totalRequestsCount.get(),
                        testCase.getId().toString(), type);

                statisticsPerSecond.add(statPerSecond);
                sendToFirebase(statPerSecond);

                requestsPerSecond.set(0);
                secondsToRun.decrementAndGet();
            } else {
                tasksExecutor.shutdownNow();
            }
        }

    }

    private void sendToFirebase(StatisticsPerSecond statisticsPerSecond) {
        FirestoreClient.getFirestore().collection("test-case-stats")
                .document(UUID.randomUUID().toString())
                .create(statisticsPerSecond);
    }

}
