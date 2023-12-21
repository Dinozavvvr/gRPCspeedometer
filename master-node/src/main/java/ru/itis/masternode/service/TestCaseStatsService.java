package ru.itis.masternode.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Filter;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import ru.itis.masternode.model.StatisticsPerSecond;
import ru.itis.masternode.model.StatisticsSummary;

@Service
public class TestCaseStatsService {

    private final String TEST_CASE_STATS = "test-case-stats";

    public StatisticsSummary getRestStatsForTestCase(UUID testCaseId) {
        return getStatsForTestCase(testCaseId, "REST");
    }

    public StatisticsSummary getGrpcStatsForTestCase(UUID testCaseId) {
        return getStatsForTestCase(testCaseId, "GRPC");
    }

    private StatisticsSummary getStatsForTestCase(UUID testCaseId, String type) {
        List<StatisticsPerSecond> resultList = new ArrayList<>();

        try {
            ApiFuture<QuerySnapshot> query = FirestoreClient.getFirestore()
                    .collection(TEST_CASE_STATS)
                    .where(Filter.and(Filter.equalTo("testCaseId", testCaseId.toString()),
                                    Filter.equalTo("type", type)))
                    .get();

            QuerySnapshot querySnapshot = query.get();

            for (QueryDocumentSnapshot document : querySnapshot) {
                StatisticsPerSecond statistics = mapDocumentToStatistics(document);
                resultList.add(statistics);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return new StatisticsSummary(resultList);
    }

    private StatisticsPerSecond mapDocumentToStatistics(QueryDocumentSnapshot document) {
        StatisticsPerSecond statistics = new StatisticsPerSecond();

        statistics.setSecond(document.getLong("second"));
        statistics.setRequestsPerSecond(document.getLong("requestsPerSecond"));
        statistics.setAverageRequestsPerSecond(document.getLong("averageRequestsPerSecond"));
        statistics.setTotalRequestCount(document.getLong("totalRequestCount"));
        statistics.setTestCaseId(document.getString("testCaseId"));
        statistics.setType(document.getString("type"));

        return statistics;
    }

}
