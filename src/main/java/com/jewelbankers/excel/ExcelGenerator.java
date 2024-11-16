package com.jewelbankers.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;

public class ExcelGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static ByteArrayInputStream generateBillExcel(List<Bill> bills, String password) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Bills");

        // Create styles for header, content, and total rows
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle contentStyle = createContentStyle(workbook);
        CellStyle totalStyle = createTotalStyle(workbook);

        // Create header row
        Row headerRow = sheet.createRow(0);
        createHeaderCell(headerRow, 0, "PledgeNo", headerStyle);
        createHeaderCell(headerRow, 1, "Bill Date", headerStyle);
        createHeaderCell(headerRow, 2, "Customer Details", headerStyle);
        createHeaderCell(headerRow, 3, "Weight", headerStyle);
        createHeaderCell(headerRow, 4, "Amount", headerStyle);
        createHeaderCell(headerRow, 5, "Product Description", headerStyle);
        createHeaderCell(headerRow, 6, "Amount in Words", headerStyle);

        int rowIdx = 1;
        double totalAmount = 0.0; // Variable to keep track of the total amount

        // Populate data rows
        for (Bill bill : bills) {
            Row row = sheet.createRow(rowIdx++);

            // Merge Bill Serial and Bill No as PledgeNo
            String pledgeNo = bill.getBillSerial().toString() + "-" + bill.getBillNo();
            createContentCell(row, 0, pledgeNo, contentStyle);

            // Format Bill Date to "dd-MM-yyyy"
            String formattedDate = bill.getBillDate() != null ? bill.getBillDate().format(DATE_FORMATTER) : "";
            createContentCell(row, 1, formattedDate, contentStyle);

            // Merge Customer Name and Address as Customer Details
            String customerDetails = (bill.getCustomer() != null ? bill.getCustomer().getCustomerName() + ", " + bill.getCustomer().getAddress() : "");
            createContentCell(row, 2, customerDetails, contentStyle);

            createContentCell(row, 3, bill.getGrams() != null ? bill.getGrams().toString() : "", contentStyle);
            createContentCell(row, 4, String.valueOf(bill.getAmount()), contentStyle);

            createContentCell(row, 6, bill.getAmountInWords(), contentStyle);

            // Add the bill amount to the total
            totalAmount += bill.getAmount();

            // Add BillDetail rows below each Bill row
            for (BillDetail detail : bill.getBillDetails()) {
                createContentCell(row, 5, detail.getProductDescription(), contentStyle);
            }
        }

        // Add the total amount row
        Row totalRow = sheet.createRow(rowIdx++);  // Increment rowIdx
        createContentCell(totalRow, 3, "Total Amount", totalStyle);

        // Format the totalAmount using the Indian numbering system format
        DecimalFormat formatter = new DecimalFormat("##,##,##,###");
        String formattedTotalAmount = formatter.format(totalAmount);
        createContentCell(totalRow, 4, formattedTotalAmount, totalStyle);

        // Auto-size columns for better alignment
        for (int i = 0; i <= 6; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        // Encrypt the Excel file with the password
        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
        Encryptor encryptor = info.getEncryptor();
        encryptor.confirmPassword(password);  // Set the password for encryption

        try {
            // Convert the raw Excel data into a ByteArrayInputStream
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());

            // Encrypt the raw data
            try (OutputStream encryptedDataStream = encryptor.getDataStream(fs)) {
                byteArrayInputStream.transferTo(encryptedDataStream);  // Encrypt the content
            }

            // Write the encrypted data to a new ByteArrayOutputStream
            ByteArrayOutputStream encryptedOut = new ByteArrayOutputStream();
            fs.writeFilesystem(encryptedOut);

            // Return the encrypted data as a ByteArrayInputStream
            return new ByteArrayInputStream(encryptedOut.toByteArray());
        } catch (GeneralSecurityException | IOException e) {
            throw new IOException("Error during Excel file encryption", e);
        }
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
        style.setAlignment(HorizontalAlignment.CENTER);
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
