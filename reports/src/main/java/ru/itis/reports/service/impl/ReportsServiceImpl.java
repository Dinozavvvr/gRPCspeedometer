package ru.itis.reports.service.impl;


import org.apache.poi.ss.usermodel.*;
import ru.itis.reports.model.StatisticsPerSecond;
import ru.itis.reports.model.StatisticsSummary;
import ru.itis.reports.model.TestCase;
import ru.itis.reports.service.ReportsService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ReportsServiceImpl implements ReportsService {
    @Override
    public void downloadExcel(TestCase testCase, StatisticsSummary rest, StatisticsSummary grpc) {

        Workbook wb = new XSSFWorkbook();
        try  (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
            Sheet sheet = wb.createSheet("Статистика");
            int row = 0;

            row = fillTestCaseInfo(testCase, sheet, row);
            Row rowBlank = sheet.createRow(row++);
            row = writeLabels(sheet, row);

            StatisticsSummary difference = statisticsDifference(rest, grpc);
            for (int i = 0; i < rest.getStatisticsPerSeconds().size(); i++) {
                Row rowTemp = sheet.createRow(row + i);

                fillCellsWithStatistics(rest, 0, rowTemp, i);
                fillCellsWithStatistics(grpc,  5, rowTemp, i);
                fillCellsWithStatistics(difference, 10, rowTemp, i);
            }

            wb.write(fileOut);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public StatisticsSummary statisticsDifference(StatisticsSummary rest, StatisticsSummary grpc) {
        List<StatisticsPerSecond> statisticsDifference = new ArrayList<>();

        for (int i = 0; i < rest.getStatisticsPerSeconds().size(); i++) {
            StatisticsPerSecond statisticsPerSecondTemp = new StatisticsPerSecond(
                    rest.getStatisticsPerSeconds().get(i).second(),
                    rest.getStatisticsPerSeconds().get(i).requestsPerSecond() - grpc.getStatisticsPerSeconds().get(i).requestsPerSecond(),
                    rest.getStatisticsPerSeconds().get(i).averageRequestsPerSecond() - grpc.getStatisticsPerSeconds().get(i).averageRequestsPerSecond(),
                    rest.getStatisticsPerSeconds().get(i).totalRequestCount() - grpc.getStatisticsPerSeconds().get(i).totalRequestCount(),
                    rest.getStatisticsPerSeconds().get(i).testCaseId()
            );
            statisticsDifference.add(statisticsPerSecondTemp);
        }

        return new StatisticsSummary(statisticsDifference);
    }

    public void fillCellsWithStatistics(StatisticsSummary statistics, int firstColumn, Row row, int i) {
        Cell secondCell = row.createCell(firstColumn);
        secondCell.setCellValue(statistics.getStatisticsPerSeconds().get(i).second());

        Cell requestsPerSecondCell = row.createCell(++firstColumn);
        requestsPerSecondCell.setCellValue(statistics.getStatisticsPerSeconds().get(i).requestsPerSecond());

        Cell averageRequestsPerSecondCell = row.createCell(++firstColumn);
        averageRequestsPerSecondCell.setCellValue(statistics.getStatisticsPerSeconds().get(i).averageRequestsPerSecond());

        Cell totalRequestCountCell = row.createCell(++firstColumn);
        totalRequestCountCell.setCellValue(statistics.getStatisticsPerSeconds().get(i).totalRequestCount());
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
        Row lablesRow = sheet.createRow(row++);
        Cell restLable = lablesRow.createCell(0);
        restLable.setCellValue("REST API");
        Cell grpcLable = lablesRow.createCell(5);
        grpcLable.setCellValue("gRPC");
        Cell compareLable = lablesRow.createCell(10);
        compareLable.setCellValue("Сравнение");

        Row lablesMethricsRow = sheet.createRow(row++);
        Cell restSecondLable = lablesMethricsRow.createCell(0);
        restSecondLable.setCellValue("Seconds");
        Cell restRequestsPerSecondLable = lablesMethricsRow.createCell(1);
        restRequestsPerSecondLable.setCellValue("RequestsPerSecond");
        Cell restAverageRequestsPerSecondLable = lablesMethricsRow.createCell(2);
        restAverageRequestsPerSecondLable.setCellValue("AverageRequestsPerSecond");
        Cell restTotalRequestCountLable = lablesMethricsRow.createCell(3);
        restTotalRequestCountLable.setCellValue("totalRequestCount");

        Cell grpcSecondLable = lablesMethricsRow.createCell(5);
        grpcSecondLable.setCellValue("Seconds");
        Cell grpcRequestsPerSecondLable = lablesMethricsRow.createCell(6);
        grpcRequestsPerSecondLable.setCellValue("RequestsPerSecond");
        Cell grpcAverageRequestsPerSecondLable = lablesMethricsRow.createCell(7);
        grpcAverageRequestsPerSecondLable.setCellValue("AverageRequestsPerSecond");
        Cell grpcTotalRequestCountLable = lablesMethricsRow.createCell(8);
        grpcTotalRequestCountLable.setCellValue("totalRequestCount");

        Cell compareSecondLable = lablesMethricsRow.createCell(10);
        compareSecondLable.setCellValue("Seconds");
        Cell compareRequestsPerSecondLable = lablesMethricsRow.createCell(11);
        compareRequestsPerSecondLable.setCellValue("RequestsPerSecond");
        Cell compareAverageRequestsPerSecondLable = lablesMethricsRow.createCell(12);
        compareAverageRequestsPerSecondLable.setCellValue("AverageRequestsPerSecond");
        Cell compareTotalRequestCountLable = lablesMethricsRow.createCell(13);
        compareTotalRequestCountLable.setCellValue("totalRequestCount");
        return row;
    }
}
