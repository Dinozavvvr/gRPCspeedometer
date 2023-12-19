package ru.itis.masternode.service;

import com.google.firebase.cloud.FirestoreClient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.itis.masternode.model.TestCase;
import ru.itis.masternode.model.TestCaseState;
import ru.itis.masternode.model.enums.RequestMethod;
import ru.itis.workernode.emumeration.FlowType;

@Service
public class TestCaseService {

    private final String TEST_CASES = "test-cases";

    @SneakyThrows
    public TestCase getTestCase(UUID testCaseId) {
        TestCase testCase = TestCaseFirebaseEntity.toTestCase(
                FirestoreClient.getFirestore().collection(TEST_CASES)
                        .document(testCaseId.toString()).get().get()
                        .toObject(TestCaseFirebaseEntity.class));

        if (testCase != null) {
            testCase.setId(testCaseId);
        }

        return testCase;
    }

    public List<TestCase> getAllTestCases() {
        List<TestCase> testCaseList = new ArrayList<>();

        FirestoreClient.getFirestore().collection(TEST_CASES)
                .listDocuments()
                .forEach(queryDocumentSnapshot -> {
                    try {
                        TestCaseFirebaseEntity testCaseFirebaseEntity = queryDocumentSnapshot.get().get()
                                .toObject(TestCaseFirebaseEntity.class);
                        if (testCaseFirebaseEntity != null) {
                            TestCase testCase = TestCaseFirebaseEntity.toTestCase(testCaseFirebaseEntity);
                            testCase.setId(UUID.fromString(queryDocumentSnapshot.getId()));
                            testCaseList.add(testCase);
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                });

        return testCaseList;
    }

    public void saveTestCase(TestCase testCase) {
        FirestoreClient.getFirestore().collection(TEST_CASES)
                .document(testCase.getId().toString())
                .create(TestCaseFirebaseEntity.fromTestCase(testCase));
    }

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
                    .build();
        }
    }

}
