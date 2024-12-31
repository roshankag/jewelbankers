package com.jewelbankers.application;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelXlsGenerator {

    public static void main(String[] args) {
        // Create an HSSFWorkbook for .xls format
        Workbook workbook = new HSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet("Sample Data");

        // Create a header row
        Row headerRow = sheet.createRow(0);
        createCell(headerRow, 0, "ID", workbook);
        createCell(headerRow, 1, "Name", workbook);
        createCell(headerRow, 2, "Amount", workbook);

        // Add data rows
        Object[][] data = {
                {1, "John Doe", 12345.67},
                {2, "Jane Smith", 98765.43},
                {3, "Sam Wilson", 54321.00}
        };

        int rowNum = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            createCell(row, 0, rowData[0], workbook);
            createCell(row, 1, rowData[1], workbook);
            createCell(row, 2, rowData[2], workbook);
        }

        // Auto-size columns
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream("SampleData.xls")) {
            workbook.write(fileOut);
            System.out.println("Excel file 'SampleData.xls' created successfully!");
        } catch (IOException e) {
            System.err.println("Error writing Excel file: " + e.getMessage());
        }

        // Close the workbook
        try {
            workbook.close();
        } catch (IOException e) {
            System.err.println("Error closing workbook: " + e.getMessage());
        }
    }

    // Helper method to create a cell with data
    private static void createCell(Row row, int column, Object value, Workbook workbook) {
        Cell cell = row.createCell(column);

        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        }

        // Apply a basic style
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        cell.setCellStyle(style);
    }
}
