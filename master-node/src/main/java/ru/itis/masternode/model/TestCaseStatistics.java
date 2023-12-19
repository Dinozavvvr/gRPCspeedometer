package ru.itis.masternode.model;

import com.google.firebase.cloud.FirestoreClient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCaseStatistics {

    private final AtomicLong totalRequestsCount = new AtomicLong(0);

    private final AtomicLong requestsPerSecond = new AtomicLong(0);

    private final AtomicLong pastSecondsCount = new AtomicLong(0);

    private final AtomicLong averageRequestsPerSecond = new AtomicLong(0);

    private final ScheduledExecutorService tasksExecutor = Executors.newScheduledThreadPool(1);

    private final TestCase testCase;

    private boolean isStarted = false;

    private ScheduledFuture<?> updatePeriodicalTaskFuture;

    private final List<StatisticsPerSecond> statisticsPerSecond = new ArrayList<>();

    public TestCaseStatistics(TestCase testCase) {
        this.testCase = testCase;
    }

    public void start(long delay) {
        if (isStarted) {
            throw new IllegalStateException("TestCaseStatistics already have been started");
        }

        updatePeriodicalTaskFuture = tasksExecutor
                .scheduleAtFixedRate(new UpdatePeriodicalStatsTask(), delay, 1000,
                        TimeUnit.MILLISECONDS);
        isStarted = true;
    }

    public void registerSuccessRequest() {
        totalRequestsCount.incrementAndGet();
        requestsPerSecond.incrementAndGet();
    }


    public StatisticsSummary stop() {
        updatePeriodicalTaskFuture.cancel(true);
        tasksExecutor.shutdownNow();

        return new StatisticsSummary(statisticsPerSecond);
    }

    private class UpdatePeriodicalStatsTask implements Runnable {

        @Override
        public void run() {
            long pastSeconds = pastSecondsCount.incrementAndGet();
            averageRequestsPerSecond.set((totalRequestsCount.get() / pastSeconds));

            log.info("------------------------");
            log.info("second: {} ", pastSeconds);
            log.info("total req count: {} ", totalRequestsCount.get());
            log.info("req per second: {} ", requestsPerSecond.get());

            StatisticsPerSecond statPerSecond = new StatisticsPerSecond(pastSeconds,
                    requestsPerSecond.get(),
                    averageRequestsPerSecond.get(), totalRequestsCount.get(),
                    testCase.getId().toString());

            statisticsPerSecond.add(statPerSecond);
            sendToFirebase(statPerSecond);

            requestsPerSecond.set(0);
        }

    }

    private void sendToFirebase(StatisticsPerSecond statisticsPerSecond) {
        FirestoreClient.getFirestore().collection("test-case-stats")
                .document(UUID.randomUUID().toString())
                .create(statisticsPerSecond);
    }

}
