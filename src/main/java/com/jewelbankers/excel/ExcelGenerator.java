package com.jewelbankers.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;
import com.jewelbankers.repository.ProductTypeRepository;

public class ExcelGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static ByteArrayInputStream generateBillExcel(List<Bill> bills, String password, 
    		ProductTypeRepository productTypeRepository) throws IOException {
    	// Choose the correct workbook type based on the file format
        Workbook workbook;
        //if (fileFormat.equalsIgnoreCase("xlsx")) {
            workbook = new XSSFWorkbook(); // Use XSSFWorkbook for .xlsx
//        } else {
//            workbook = new HSSFWorkbook(); // Use HSSFWorkbook for .xls
//        }
    	
        Sheet sheet = workbook.createSheet("Bills");

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
        AtomicReference<BigDecimal> totalGoldWeight = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> totalSilverWeight = new AtomicReference<>(BigDecimal.ZERO);

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
                
                productTypeRepository.findByProductTypeNo(bill.getProductTypeNo()).ifPresent(productType -> {
                    // Use the correct method to get the name of the product type
                    String productName = productType.getProductTypeCode(); // Replace 'getName' with the actual method

                    if ("Gold".equalsIgnoreCase(productName)) {
                        totalGoldWeight.set(totalGoldWeight.get().add(bill.getGrams() != null ? bill.getGrams() : BigDecimal.ZERO));
                    } else if ("Silver".equalsIgnoreCase(productName)) {
                        totalSilverWeight.set(totalSilverWeight.get().add(bill.getGrams() != null ? bill.getGrams() : BigDecimal.ZERO));
                    }
                });
            }
        }
        

        // Add the total amount row
        Row totalRow = sheet.createRow(rowIdx++);  // Increment rowIdx
        createContentCell(totalRow, 3, "Total Amount", totalStyle);

        // Format the totalAmount using the Indian numbering system format
        DecimalFormat formatter = new DecimalFormat("##,##,##,###");
        String formattedTotalAmount = formatter.format(totalAmount);
        createContentCell(totalRow, 4, formattedTotalAmount, totalStyle);
        
        
     // Add total gold weight row
        Row goldWeightRow = sheet.createRow(rowIdx++);
        createContentCell(goldWeightRow, 3, "Total Gold Weight", createGoldWeightStyle(workbook));
        createContentCell(goldWeightRow, 4, String.valueOf(totalGoldWeight), createGoldWeightStyle(workbook));

        // Add total silver weight row
        Row silverWeightRow = sheet.createRow(rowIdx++);
        createContentCell(silverWeightRow, 3, "Total Silver Weight", createSilverWeightStyle(workbook));
        createContentCell(silverWeightRow, 4, String.valueOf(totalSilverWeight), createSilverWeightStyle(workbook));
        

        // Auto-size columns for better alignment
        for (int i = 0; i <= 6; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
 
        ByteArrayInputStream byteArrayInputStream = null;
        try {
        	//if (fileFormat.equalsIgnoreCase("xlsx")) {
        		
            	byteArrayInputStream = getByteArrayForPasswordProtectedXLSX(password, out);            

            	
//            } else {
//            	
//            	byteArrayInputStream = getByteArrayForPasswordProtectedXLSX(password, out);
//            	}
//        	
        } catch (Exception e) {
           throw new IOException("Error during Excel file encryption", e);
        }
        finally {
        	//fs.close();
        	workbook.close();
        	out.close();
        }
        return byteArrayInputStream;
    }
    
	/*
	 * private static ByteArrayInputStream getPasswordProtectedXLS(ByteArrayOutputStream
	 * out) { // Convert the raw Excel data into a ByteArrayInputStream
	 * ByteArrayInputStream byteArrayInputStream = new
	 * ByteArrayInputStream(out.toByteArray()); return byteArrayInputStream; }
	 */
	/*
	 * private static ByteArrayInputStream
	 * getPasswordProtectedXLS(ByteArrayOutputStream rawExcelOutput, String
	 * password) throws IOException { // Step 1: Create a POIFSFileSystem instance
	 * for adding encryption try (POIFSFileSystem fs = new POIFSFileSystem()) {
	 * 
	 * // Step 2: Write the raw Excel data into a document within the filesystem try
	 * (InputStream rawExcelInput = new
	 * ByteArrayInputStream(rawExcelOutput.toByteArray())) {
	 * fs.createDocument(rawExcelInput, "Workbook"); }
	 * 
	 * // Step 3: Set the password using Biff8EncryptionKey
	 * //Biff8EncryptionKey.setCurrentUserPassword(password);
	 * 
	 * // Step 4: Save the encrypted filesystem to a ByteArrayOutputStream
	 * ByteArrayOutputStream encryptedOutput = new ByteArrayOutputStream();
	 * fs.writeFilesystem(encryptedOutput);
	 * 
	 * // Step 5: Clear the password for security
	 * //Biff8EncryptionKey.setCurrentUserPassword(null);
	 * 
	 * // Step 6: Return the encrypted data as a ByteArrayInputStream return new
	 * ByteArrayInputStream(encryptedOutput.toByteArray()); } }
	 */

    
    private static ByteArrayInputStream getByteArrayForPasswordProtectedXLSX(String password, ByteArrayOutputStream out) throws Exception {
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
        } catch (IOException e) {
            throw new IOException("Error during Excel file encryption", e);
        }
        finally {
        	fs.close();
        	//workbook.close();
        	out.close();
        }
    }

    // Helper method to create a cell with content
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
    private static CellStyle createHeaderStyle(Workbook workbook) {
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
    private static CellStyle createContentStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    // Create style for the total amount row
    private static CellStyle createTotalStyle(Workbook workbook) {
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
    
 // Create style for the total gold weight row
    private static CellStyle createGoldWeightStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.DARK_GREEN.getIndex()); // Green font for gold weight
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Light green background
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    // Create style for the total silver weight row
    private static CellStyle createSilverWeightStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.DARK_BLUE.getIndex()); // Blue font for silver weight
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex()); // Light blue background
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

}