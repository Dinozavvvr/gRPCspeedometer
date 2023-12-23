package ru.itis.masternode.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.http.ResponseEntity;
import ru.itis.masternode.model.StatisticsSummary;
import ru.itis.masternode.model.TestCase;

public interface ReportsService {

    ResponseEntity<byte[]> downloadExcel(TestCase testCase);

    void fillCellsWithStatistics(StatisticsSummary statistics, int firstColumn, Row row, int i);

    int fillTestCaseInfo(TestCase testCase, Sheet sheet, int row);

    int writeLabels(Sheet sheet, int row);


}
