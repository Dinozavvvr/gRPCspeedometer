package ru.itis.masternode.service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.itis.masternode.model.*;

@Service
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    @Override
    public ResponseEntity<byte[]> downloadExcel(TestCase testCase) {

        StatisticsSummary rest = testCase.getRestStatistics();
        rest.getStatisticsPerSeconds().sort(Comparator.comparing(StatisticsPerSecond::getSecond));
        StatisticsSummary grpc = testCase.getGrpcStatistics();
        grpc.getStatisticsPerSeconds().sort(Comparator.comparing(StatisticsPerSecond::getSecond));

        try (Workbook wb = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("Статистика");
            int row = 0;

            row = fillTestCaseInfo(testCase, sheet, row);
            row = writeLabels(sheet, row);

            StatisticsSummary difference = statisticsDifference(rest, grpc);
            StatisticsSummaryInTimes differenceInTimes = statisticsDifferenceInTimes(rest, grpc);
            for (int i = 0; i < rest.getStatisticsPerSeconds().size(); i++) {
                Row rowTemp = sheet.createRow(row + i);

                fillCellsWithStatistics(rest, 0, rowTemp, i);
                fillCellsWithStatistics(grpc, 5, rowTemp, i);
                fillCellsWithStatistics(difference, 10, rowTemp, i);
                fillCellsWithStatisticsInTimes(differenceInTimes, 15, rowTemp, i);
            }

            wb.write(outputStream);
            byte[] data = outputStream.toByteArray();

            return ResponseEntity.ok()
                    .header("Content-Disposition",
                            "attachment; filename=" + testCase.getId().toString() + ".xls")
                    .body(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public StatisticsSummary statisticsDifference(StatisticsSummary rest, StatisticsSummary grpc) {
        List<StatisticsPerSecond> statisticsDifference = new ArrayList<>();

        for (int i = 0; i < rest.getStatisticsPerSeconds().size(); i++) {
            StatisticsPerSecond statisticsPerSecondTemp = new StatisticsPerSecond(
                    rest.getStatisticsPerSeconds().get(i).getSecond(),
                    rest.getStatisticsPerSeconds().get(i).getRequestsPerSecond()
                            - grpc.getStatisticsPerSeconds().get(i).getRequestsPerSecond(),
                    rest.getStatisticsPerSeconds().get(i).getAverageRequestsPerSecond()
                            - grpc.getStatisticsPerSeconds().get(i).getAverageRequestsPerSecond(),
                    rest.getStatisticsPerSeconds().get(i).getTotalRequestCount()
                            - grpc.getStatisticsPerSeconds().get(i).getTotalRequestCount(),
                    rest.getStatisticsPerSeconds().get(i).getTestCaseId(), null
            );
            statisticsDifference.add(statisticsPerSecondTemp);
        }

        return new StatisticsSummary(statisticsDifference);
    }

    public StatisticsSummaryInTimes statisticsDifferenceInTimes(StatisticsSummary rest, StatisticsSummary grpc) {
        List<StatisticsPerSecondInTimes> statisticsDifference = new ArrayList<>();

        for (int i = 0; i < rest.getStatisticsPerSeconds().size(); i++) {
            StatisticsPerSecondInTimes statisticsPerSecondTemp = new StatisticsPerSecondInTimes(
                    rest.getStatisticsPerSeconds().get(i).getSecond(),
                    (double) grpc.getStatisticsPerSeconds().get(i).getRequestsPerSecond()
                            / rest.getStatisticsPerSeconds().get(i).getRequestsPerSecond(),
                    (double) grpc.getStatisticsPerSeconds().get(i).getAverageRequestsPerSecond()
                            / rest.getStatisticsPerSeconds().get(i).getAverageRequestsPerSecond(),
                    (double) grpc.getStatisticsPerSeconds().get(i).getTotalRequestCount()
                            / rest.getStatisticsPerSeconds().get(i).getTotalRequestCount(),
                    rest.getStatisticsPerSeconds().get(i).getTestCaseId()
            );
            statisticsDifference.add(statisticsPerSecondTemp);
        }

        return new StatisticsSummaryInTimes(statisticsDifference);
    }

    public void fillCellsWithStatistics(StatisticsSummary statistics, int firstColumn, Row row,
            int i) {
        Cell secondCell = row.createCell(firstColumn);
        secondCell.setCellValue(statistics.getStatisticsPerSeconds().get(i).getSecond());

        Cell requestsPerSecondCell = row.createCell(++firstColumn);
        requestsPerSecondCell.setCellValue(
                statistics.getStatisticsPerSeconds().get(i).getRequestsPerSecond());

        Cell averageRequestsPerSecondCell = row.createCell(++firstColumn);
        averageRequestsPerSecondCell.setCellValue(
                statistics.getStatisticsPerSeconds().get(i).getAverageRequestsPerSecond());

        Cell totalRequestCountCell = row.createCell(++firstColumn);
        totalRequestCountCell.setCellValue(
                statistics.getStatisticsPerSeconds().get(i).getTotalRequestCount());
    }

    public void fillCellsWithStatisticsInTimes(StatisticsSummaryInTimes statistics, int firstColumn, Row row,
                                        int i) {
        Cell secondCell = row.createCell(firstColumn);
        secondCell.setCellValue(statistics.getStatisticsPerSeconds().get(i).getSecond());

        Cell requestsPerSecondCell = row.createCell(++firstColumn);
        requestsPerSecondCell.setCellValue(
                statistics.getStatisticsPerSeconds().get(i).getRequestsPerSecond());

        Cell averageRequestsPerSecondCell = row.createCell(++firstColumn);
        averageRequestsPerSecondCell.setCellValue(
                statistics.getStatisticsPerSeconds().get(i).getAverageRequestsPerSecond());

        Cell totalRequestCountCell = row.createCell(++firstColumn);
        totalRequestCountCell.setCellValue(
                statistics.getStatisticsPerSeconds().get(i).getTotalRequestCount());
    }

    public int fillTestCaseInfo(TestCase testCase, Sheet sheet, int row) {
        Row threadsCountRow = sheet.createRow(row++);
        Cell threadsCountCellTag = threadsCountRow.createCell(0);
        threadsCountCellTag.setCellValue("Количество потоков");
        Cell threadsCountCellValue = threadsCountRow.createCell(1);
        threadsCountCellValue.setCellValue(testCase.getThreadsCount());

        Row requestDepthRow = sheet.createRow(row++);
        Cell requestDepthCellTag = requestDepthRow.createCell(0);
        requestDepthCellTag.setCellValue("Глубина запроса");
        Cell requestDepthCellValue = requestDepthRow.createCell(1);
        requestDepthCellValue.setCellValue(testCase.getRequestDepth());

        Row workTimeRow = sheet.createRow(row++);
        Cell workTimeCellTag = workTimeRow.createCell(0);
        workTimeCellTag.setCellValue("Время запроса");
        Cell workTimeCellValue = workTimeRow.createCell(1);
        workTimeCellValue.setCellValue(testCase.getWorkTime());

        Row requestMethodRow = sheet.createRow(row++);
        Cell requestMethodCellTag = requestMethodRow.createCell(0);
        requestMethodCellTag.setCellValue("Тип запроса");
        Cell requestMethodeCellValue = requestMethodRow.createCell(1);
        requestMethodeCellValue.setCellValue(testCase.getRequestMethod().toString());

        Row flowTypeRow = sheet.createRow(row++);
        Cell flowTypeCellTag = flowTypeRow.createCell(0);
        flowTypeCellTag.setCellValue("Тип потока");
        Cell flowTypeCellValue = flowTypeRow.createCell(1);
        flowTypeCellValue.setCellValue(testCase.getFlowType().toString());

        Row requestBodySizeRow = sheet.createRow(row++);
        Cell requestBodySizeCellTag = requestBodySizeRow.createCell(0);
        requestBodySizeCellTag.setCellValue("Размер запроса");
        Cell requestBodySizeCellValue = requestBodySizeRow.createCell(1);
        requestBodySizeCellValue.setCellValue(testCase.getRequestBodySize());
        return row;
    }

    public int writeLabels(Sheet sheet, int row) {
        Row labelsRow = sheet.createRow(row++);
        Cell restLabel = labelsRow.createCell(0);
        restLabel.setCellValue("REST API");
        Cell grpcLabel = labelsRow.createCell(5);
        grpcLabel.setCellValue("gRPC");
        Cell compareLabel = labelsRow.createCell(10);
        compareLabel.setCellValue("Сравнение");
        Cell compareInTimesLabel = labelsRow.createCell(15);
        compareInTimesLabel.setCellValue("Сравнение во сколько раз");


        Row labelsMetricsRow = sheet.createRow(row++);
        Cell restSecondLabel = labelsMetricsRow.createCell(0);
        restSecondLabel.setCellValue("Seconds");
        Cell restRequestsPerSecondLabel = labelsMetricsRow.createCell(1);
        restRequestsPerSecondLabel.setCellValue("RequestsPerSecond");
        Cell restAverageRequestsPerSecondLabel = labelsMetricsRow.createCell(2);
        restAverageRequestsPerSecondLabel.setCellValue("AverageRequestsPerSecond");
        Cell restTotalRequestCountLabel = labelsMetricsRow.createCell(3);
        restTotalRequestCountLabel.setCellValue("totalRequestCount");

        Cell grpcSecondLabel = labelsMetricsRow.createCell(5);
        grpcSecondLabel.setCellValue("Seconds");
        Cell grpcRequestsPerSecondLabel = labelsMetricsRow.createCell(6);
        grpcRequestsPerSecondLabel.setCellValue("RequestsPerSecond");
        Cell grpcAverageRequestsPerSecondLabel = labelsMetricsRow.createCell(7);
        grpcAverageRequestsPerSecondLabel.setCellValue("AverageRequestsPerSecond");
        Cell grpcTotalRequestCountLabel = labelsMetricsRow.createCell(8);
        grpcTotalRequestCountLabel.setCellValue("totalRequestCount");

        Cell compareSecondLabel = labelsMetricsRow.createCell(10);
        compareSecondLabel.setCellValue("Seconds");
        Cell compareRequestsPerSecondLabel = labelsMetricsRow.createCell(11);
        compareRequestsPerSecondLabel.setCellValue("RequestsPerSecond");
        Cell compareAverageRequestsPerSecondLabel = labelsMetricsRow.createCell(12);
        compareAverageRequestsPerSecondLabel.setCellValue("AverageRequestsPerSecond");
        Cell compareTotalRequestCountLabel = labelsMetricsRow.createCell(13);
        compareTotalRequestCountLabel.setCellValue("totalRequestCount");

        Cell compareSecondInTimesLabel = labelsMetricsRow.createCell(15);
        compareSecondInTimesLabel.setCellValue("Seconds");
        Cell compareRequestsPerSecondInTimesLabel = labelsMetricsRow.createCell(16);
        compareRequestsPerSecondInTimesLabel.setCellValue("RequestsPerSecond");
        Cell compareAverageRequestsPerSecondInTimesLabel = labelsMetricsRow.createCell(17);
        compareAverageRequestsPerSecondInTimesLabel.setCellValue("AverageRequestsPerSecond");
        Cell compareTotalRequestCountInTimesLabel = labelsMetricsRow.createCell(18);
        compareTotalRequestCountInTimesLabel.setCellValue("totalRequestCount");
        return row;
    }

}
