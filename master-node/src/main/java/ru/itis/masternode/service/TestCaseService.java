package ru.itis.masternode.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Query.Direction;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.itis.masternode.model.TestCase;
import ru.itis.masternode.model.TestCaseState;
import ru.itis.masternode.model.enums.RequestMethod;
import ru.itis.workernode.emumeration.FlowType;

@Service
@RequiredArgsConstructor
public class TestCaseService {

    private final String TEST_CASES = "test-cases";

    private final TestCaseStatsService testCaseStatsService;

    @SneakyThrows
    public TestCase getTestCase(UUID testCaseId) {
        TestCase testCase = TestCaseFirebaseEntity.toTestCase(
                FirestoreClient.getFirestore().collection(TEST_CASES)
                        .document(testCaseId.toString()).get().get()
                        .toObject(TestCaseFirebaseEntity.class));

        if (testCase != null) {
            testCase.setId(testCaseId);
            testCase.setRestStatistics(testCaseStatsService.getRestStatsForTestCase(testCaseId));
            testCase.setGrpcStatistics(testCaseStatsService.getGrpcStatsForTestCase(testCaseId));
        }

        return testCase;
    }

    public List<TestCase> getAllTestCases() {
        List<TestCase> testCaseList = new ArrayList<>();

        try {
            ApiFuture<QuerySnapshot> query = FirestoreClient.getFirestore()
                    .collection(TEST_CASES)
                    .limit(10)
                    .orderBy("createdAt", Direction.DESCENDING)
                    .get();

            QuerySnapshot querySnapshot = query.get();

            for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshot) {
                TestCaseFirebaseEntity testCaseFirebaseEntity = queryDocumentSnapshot
                        .toObject(TestCaseFirebaseEntity.class);

                TestCase testCase = TestCaseFirebaseEntity.toTestCase(testCaseFirebaseEntity);
                testCase.setId(UUID.fromString(queryDocumentSnapshot.getId()));
                testCaseList.add(testCase);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return testCaseList;
    }

    public void saveTestCase(TestCase testCase) {
        FirestoreClient.getFirestore().collection(TEST_CASES)
                .document(testCase.getId().toString())
                .create(TestCaseFirebaseEntity.fromTestCase(testCase));
    }

    @SneakyThrows
    public void updateTestCase(TestCase testCase) {
        FirestoreClient.getFirestore().collection(TEST_CASES)
                .document(testCase.getId().toString())
                .update("state", testCase.getState().name());
    }

    public void deleteTestCase(UUID testCaseId) {
        FirestoreClient.getFirestore().collection(TEST_CASES)
                .document(testCaseId.toString()).delete();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestCaseFirebaseEntity {

        private int threadsCount;

        private int requestDepth;

        private long createdAt;

        private int workTime;

        private int requestBodySize;

        private String requestMethod;

        private String flowType;

        private String state;

        public static TestCaseFirebaseEntity fromTestCase(TestCase testCase) {
            return TestCaseFirebaseEntity.builder()
                    .flowType(testCase.getFlowType().name())
                    .requestBodySize(testCase.getRequestBodySize())
                    .requestMethod(testCase.getRequestMethod().name())
                    .requestDepth(testCase.getRequestDepth())
                    .workTime(testCase.getWorkTime())
                    .threadsCount(testCase.getThreadsCount())
                    .state(testCase.getState().name())
                    .createdAt(testCase.getCreatedAt())
                    .build();
        }

        public static TestCase toTestCase(@Nullable TestCaseFirebaseEntity testCaseFirebaseEntity) {
            if (testCaseFirebaseEntity == null) {
                return null;
            }

            return TestCase.builder()
                    .flowType(FlowType.valueOf(testCaseFirebaseEntity.getFlowType()))
                    .requestBodySize(testCaseFirebaseEntity.getRequestBodySize())
                    .requestMethod(RequestMethod.valueOf(testCaseFirebaseEntity.getRequestMethod()))
                    .requestDepth(testCaseFirebaseEntity.getRequestDepth())
                    .workTime(testCaseFirebaseEntity.getWorkTime())
                    .threadsCount(testCaseFirebaseEntity.getThreadsCount())
                    .state(TestCaseState.valueOf(testCaseFirebaseEntity.getState()))
                    .createdAt(testCaseFirebaseEntity.getCreatedAt())
                    .build();
        }

    }

}
