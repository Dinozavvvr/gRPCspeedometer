package ru.itis.reports.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.http.ResponseEntity;
import ru.itis.reports.model.StatisticsSummary;
import ru.itis.reports.model.TestCase;

public interface ReportsService {
    ResponseEntity<byte[]> downloadExcel(TestCase testCase,
                                         StatisticsSummary restStatisticsPerSeconds,
                                         StatisticsSummary grpcStatisticsPerSeconds);
    void fillCellsWithStatistics(StatisticsSummary statistics, int firstColumn, Row row, int i);
    int fillTestCaseInfo(TestCase testCase, Sheet sheet, int row);
    int writeLabels(Sheet sheet, int row);
}
