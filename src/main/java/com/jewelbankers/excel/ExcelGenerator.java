package com.jewelbankers.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static ByteArrayInputStream generateBillExcel(List<Bill> bills) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Bills");

        // Create styles for header, content, and total rows
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle contentStyle = createContentStyle(workbook);
        CellStyle totalStyle = createTotalStyle(workbook);

        // Create header row
        Row headerRow = sheet.createRow(0);
        createHeaderCell(headerRow, 0, "Bill Serial", headerStyle);
        createHeaderCell(headerRow, 1, "Bill No", headerStyle);
        createHeaderCell(headerRow, 2, "Customer Name", headerStyle);
        createHeaderCell(headerRow, 3, "Address", headerStyle);
        createHeaderCell(headerRow, 4, "Weight (grams)", headerStyle);
        createHeaderCell(headerRow, 5, "Amount", headerStyle);
        createHeaderCell(headerRow, 6, "Product Description", headerStyle);
        createHeaderCell(headerRow, 7, "Bill Date", headerStyle);
        createHeaderCell(headerRow, 8, "Amount in Words", headerStyle);

        int rowIdx = 1;
        double totalAmount = 0.0; // Variable to keep track of the total amount

        // Populate data rows
        for (Bill bill : bills) {
            Row row = sheet.createRow(rowIdx++);

            createContentCell(row, 0, bill.getBillSerial().toString(), contentStyle);
            createContentCell(row, 1, String.valueOf(bill.getBillNo()), contentStyle);
            createContentCell(row, 2, bill.getCustomer() != null ? bill.getCustomer().getCustomerName() : "", contentStyle);
            
            StringBuffer address = new StringBuffer();
            if (bill.getCustomer() != null) {
                address.append(bill.getCustomer().getStreet()).append(", ")
                       .append(bill.getCustomer().getArea()).append(", ")
                       .append(bill.getCustomer().getDistrict()).append(", ")
                       .append(bill.getCustomer().getState()).append(", ")
                       .append(bill.getCustomer().getCountry()).append(", ")
                       .append(bill.getCustomer().getPincode()).append(", ")
                       .append(bill.getCustomer().getMobileno());
            }
            createContentCell(row, 3, address.toString(), contentStyle);
            createContentCell(row, 4, bill.getGrams() != null ? bill.getGrams().toString() : "", contentStyle);
            createContentCell(row, 5, String.valueOf(bill.getAmount()), contentStyle);

            // Format Bill Date to "dd-MM-yyyy"
            String formattedDate = bill.getBillDate() != null ? bill.getBillDate().format(DATE_FORMATTER) : "";
            createContentCell(row, 7, formattedDate, contentStyle);

            createContentCell(row, 8, bill.getAmountInWords(), contentStyle);

            // Add the bill amount to the total
            totalAmount += bill.getAmount();

            // Add BillDetail rows below each Bill row
            for (BillDetail detail : bill.getBillDetails()) {
                createContentCell(row, 6, detail.getProductDescription(), contentStyle);
            }
        }

        // Create a row to display the total amount at the bottom
        Row totalRow = sheet.createRow(rowIdx++);
        createContentCell(totalRow, 4, "Total Amount", totalStyle);
        createContentCell(totalRow, 5, String.valueOf(totalAmount), totalStyle);

        // Auto-size columns for better alignment
        for (int i = 0; i <= 8; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    // Helper method to create a cell with content
    private static void createContentCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    // Helper method to create a header cell
    private static void createHeaderCell(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    // Create style for the header
    private static CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    // Create style for content
    private static CellStyle createContentStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    // Create style for the total amount row
    private static CellStyle createTotalStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}


//package com.jewelbankers.excel;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import com.jewelbankers.entity.Bill;
//import com.jewelbankers.entity.BillDetail;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//public class ExcelGenerator {
//
//    public static ByteArrayInputStream generateBillExcel(List<Bill> bills) throws IOException {
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Bills");
//
//        // Create styles for header and content
//        CellStyle headerStyle = createHeaderStyle(workbook);
//        CellStyle contentStyle = createContentStyle(workbook);
//        CellStyle totalStyle = createTotalStyle(workbook);
//
//        // Create header row
//        Row headerRow = sheet.createRow(0);
//        createHeaderCell(headerRow, 0, "Bill Serial", headerStyle);
//        createHeaderCell(headerRow, 1, "Bill No", headerStyle);
//        createHeaderCell(headerRow, 2, "Customer Name", headerStyle);
//        createHeaderCell(headerRow, 3, "Address", headerStyle);
//        createHeaderCell(headerRow, 4, "Weight (grams)", headerStyle);
//        createHeaderCell(headerRow, 5, "Amount", headerStyle);
//        createHeaderCell(headerRow, 6, "Product Description", headerStyle);
//        createHeaderCell(headerRow, 7, "Bill Date", headerStyle);
//        createHeaderCell(headerRow, 8, "Amount in Words", headerStyle);
//
//        int rowIdx = 1;
//        double totalAmount = 0.0; // Variable to keep track of the total amount
//
//        // Populate data rows
//        for (Bill bill : bills) {
//            Row row = sheet.createRow(rowIdx++);
//
//            createContentCell(row, 0, bill.getBillSerial().toString(), contentStyle);
//            createContentCell(row, 1, String.valueOf(bill.getBillNo()), contentStyle);
//            createContentCell(row, 2, bill.getCustomer() != null ? bill.getCustomer().getCustomerName() : "", contentStyle);
//            
//            StringBuffer address = new StringBuffer();
//            if (bill.getCustomer() != null) {
//                address.append(bill.getCustomer().getStreet())
//                       .append(bill.getCustomer().getArea())
//                       .append(bill.getCustomer().getDistrict())
//                       .append(bill.getCustomer().getState())
//                       .append(bill.getCustomer().getCountry())
//                       .append(bill.getCustomer().getPincode())
//                       .append(bill.getCustomer().getMobileno());
//            }
//            createContentCell(row, 3, address.toString(), contentStyle);
//            createContentCell(row, 4, bill.getGrams() != null ? bill.getGrams().toString() : "", contentStyle);
//            createContentCell(row, 5, String.valueOf(bill.getAmount()), contentStyle);
//            createContentCell(row, 7, bill.getBillDate() != null ? bill.getBillDate().toString() : "", contentStyle);
//            createContentCell(row, 8, bill.getAmountInWords(), contentStyle);
//
//            // Add the bill amount to the total
//            totalAmount += bill.getAmount();
//
//            // Add BillDetail rows below each Bill row
//            for (BillDetail detail : bill.getBillDetails()) {
//                createContentCell(row, 6, detail.getProductDescription(), contentStyle);
//            }
//        }
//
//        // Create a row to display the total amount at the bottom
//        Row totalRow = sheet.createRow(rowIdx++);
//        createContentCell(totalRow, 4, "Total Amount", totalStyle);
//        createContentCell(totalRow, 5, String.valueOf(totalAmount), totalStyle);
//
//        // Auto-size columns for better alignment
//        for (int i = 0; i <= 8; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        workbook.write(out);
//        workbook.close();
//
//        return new ByteArrayInputStream(out.toByteArray());
//    }
//
//    // Helper method to create a cell with content
//    private static void createContentCell(Row row, int col, String value, CellStyle style) {
//        Cell cell = row.createCell(col);
//        cell.setCellValue(value);
//        cell.setCellStyle(style);
//    }
//
//    // Helper method to create a header cell
//    private static void createHeaderCell(Row row, int col, String value, CellStyle style) {
//        Cell cell = row.createCell(col);
//        cell.setCellValue(value);
//        cell.setCellStyle(style);
//    }
//
//    // Create style for the header
//    private static CellStyle createHeaderStyle(XSSFWorkbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setBold(true);
//        style.setFont(font);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderTop(BorderStyle.THIN);
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
//        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        return style;
//    }
//
//    // Create style for content
//    private static CellStyle createContentStyle(XSSFWorkbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        style.setAlignment(HorizontalAlignment.LEFT);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderTop(BorderStyle.THIN);
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
//        return style;
//    }
//
//    // Create style for the total amount row
//    private static CellStyle createTotalStyle(XSSFWorkbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setBold(true);
//        style.setFont(font);
//        style.setAlignment(HorizontalAlignment.RIGHT);
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderTop(BorderStyle.THIN);
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
//        return style;
//    }
//}
//
