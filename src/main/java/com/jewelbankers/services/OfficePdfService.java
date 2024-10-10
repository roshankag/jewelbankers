package com.jewelbankers.services;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.jewelbankers.Utility.SettingsUtillity;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.Settings;

@Service
public class OfficePdfService {

    @Autowired
    SettingsUtillity settingsUtillity;
    
    @Autowired
    SettingsService settingsService;

    private static final String TEMPLATE_PATH = "template/officecopy.pdf";
    private static final String OUTPUT_PATH = "bills";

    public ByteArrayInputStream generateOfficeBillPdf(Bill bill, Map<String, String> settingsMap) throws IOException, DocumentException {
        // Ensure the output directory exists
        File dir = new File(OUTPUT_PATH);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Set output file path
        String outputFileName = "bill_" + bill.getBillSerial() + "_" + bill.getBillNo() + ".pdf";
        String outputFilePath = OUTPUT_PATH + File.separator + outputFileName;

        PdfReader reader = null;
        PdfStamper stamper = null;
        FileOutputStream fos = null;

        try {
            // Ensure essential settings are not null
            String shopName = settingsMap.getOrDefault("SHOP_NAME", "Shop Name");
            String shopLine1 = settingsMap.getOrDefault("SHOP_NO", "") + " " + settingsMap.getOrDefault("SHOP_STREET", "");
            String shopLine2 = settingsMap.getOrDefault("SHOP_AREA", "") + " " + settingsMap.getOrDefault("SHOP_CITY", "");
            String shopLine3 = settingsMap.getOrDefault("SHOP_STATE", "") + " - " + settingsMap.getOrDefault("SHOP_PINCODE", "");

            // Read the template PDF
            reader = new PdfReader(TEMPLATE_PATH);

            // Create a new PDF with the content filled
            fos = new FileOutputStream(outputFilePath);
            stamper = new PdfStamper(reader, fos);

            // Set the page size to A5
            Rectangle a5Size = PageSize.A5;
            stamper.getWriter().setPageSize(a5Size); // Force the page size to A5

            // Get the PDF content
            PdfContentByte content = stamper.getOverContent(1);

            // Set the font and size	
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            Font boldFont = new Font(baseFont, 14, Font.BOLD);
            Font regularFont = new Font(baseFont, 12, Font.NORMAL);
            
         // Shop Name (BOLD and CENTERED)
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 16);  // Bold font for Shop Name, you can adjust the font size as needed
            content.setColorFill(BaseColor.BLACK);
            content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
            content.setLineWidth(0.5f);
            content.showTextAligned(Element.ALIGN_CENTER, shopName, 300, 730, 0);  // Center alignment with coordinates (300, 745)
            content.endText();

         // Shop Address (Regular Font, split into three lines)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(Element.ALIGN_CENTER, shopLine1, 300, 715, 0); // First line
            content.showTextAligned(Element.ALIGN_CENTER, shopLine2, 300, 700, 0); // Second line
            content.showTextAligned(Element.ALIGN_CENTER, shopLine3, 300, 685, 0); // Third line
            content.endText();

         // Bill Serial and No (Ensure they are not null)
            content.beginText();
            // Set font to bold, size to 14, and make text black
            content.setFontAndSize(boldFont.getBaseFont(), 13);  // Use a bold font
            content.setColorFill(BaseColor.BLACK);  // Set text color to black

            // Simulate more boldness by setting a thicker stroke width
            content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
            content.setLineWidth(0.5f);  // Increase line width for more bold effect

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, 
                bill.getBillSerial() + " " + (bill.getBillNo() != null ? bill.getBillNo().toString() : "N/A"), 
                90, 642, 0);
            content.endText();


         // Define the desired output format
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Assuming bill.getBillDate() returns a LocalDate
            LocalDate billDate = bill.getBillDate(); // This should return the date as a LocalDate
            String billDateFormatted;

            // Format the LocalDate object into the desired format
            if (billDate != null) {
                billDateFormatted = billDate.format(outputFormatter);
            } else {
                billDateFormatted = "N/A"; // Default value if the bill date is null
            }

            // Bill Date (Ensure it's not null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, billDateFormatted, 460, 642, 0);
            content.endText();

         //	 (Ensure it's not null)
            String customerName = bill.getCustomer() != null && bill.getCustomer().getCustomerName() != null 
                ? bill.getCustomer().getCustomerName() : "Customer Name";
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 13);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, customerName, 60, 590, 0);
            content.endText();

         // Customer Address (Split into lines and ensure it's not null)
            String[] addressLines = bill.getCustomer() != null && bill.getCustomer().getAddress() != null 
                ? bill.getCustomer().getAddress().split(",") : new String[]{"Address Line 1", "Address Line 2", "Address Line 3"};

            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines.length > 0 ? addressLines[0] : "", 60, 570, 0); // First line of address
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines.length > 1 ? addressLines[1] : "", 57, 550, 0); // Second line of address
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines.length > 2 ? addressLines[2] : "", 57, 530, 0); // Third line of address
            content.endText();

         // Bill Details (Ensure non-null and non-empty lists)
            if (bill.getBillDetails() != null && !bill.getBillDetails().isEmpty()) {
                content.beginText();
                content.setFontAndSize(regularFont.getBaseFont(), 14);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillDetails().get(0).getProductDescription() != null 
                    ? bill.getBillDetails().get(0).getProductDescription() : "N/A", 30, 458, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getBillDetails().get(0).getProductQuantity()), 390, 458, 0);
                content.endText();
            }

         // Grams (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getGrams() != null ? bill.getGrams() : "N/A"), 495, 465, 0);
            content.endText();
            
         // NetWt->Grams (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getGrams() != null ? bill.getGrams() : "N/A"), 495, 350, 0);
            content.endText();	
            
         // Amount Value (Ensure non-null)
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 18);
            content.setLineWidth(0.75f); // Increase the outline width
            content.setColorStroke(new BaseColor(0, 0, 0)); // Outline color (black)
            content.setColorFill(new BaseColor(0, 0, 0)); // Fill color (black)
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rs. " + String.valueOf(bill.getAmount() != null ? bill.getAmount() : "N/A"), 280, 320, 0);
            content.endText();
            
            // AmountInWords Value (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getAmountInWords() != null ? bill.getAmountInWords() : "N/A"), 30, 300, 0);
            content.endText();

         // Present Value (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getPresentValue() != null ? bill.getPresentValue() : "N/A"), 495, 295, 0);
            content.endText();
            
            // Fetch settings from the database
            List<Settings> settingsList = settingsService.getSettings();
            
            // Convert the settings list to a map
        //    Map<String, String> settingsMap = settingsUtillity.convertListToMap(settingsList);
            
            settingsMap = settingsUtillity.convertListToMap(settingsList);
            
            // Fetch pledge rules using the utility method
            String pledgeRules = settingsUtillity.getPledgeRules(settingsMap);
            
            // Check if pledgeRules is null and set a default if necessary
            if (pledgeRules == null) {
                pledgeRules = "Default rules text";  // Use your desired default text
            }

         // Split the pledgeRules into an array of lines
            String[] lines = pledgeRules.split("\n");

            // Set the starting position for the first line
            float yPosition = 250; // Starting Y position for A5
            float lineSpacing = 20; // Space between lines (adjust as necessary)

            // Display each line in the PDF content with adjusted alignment
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 14);  // Adjust the font size as needed
            content.setColorFill(new BaseColor(0, 0, 0));

            // Aligning each line differently
            for (int i = 0; i < lines.length; i++) {
                if (yPosition < 40) { // Check if the Y position is below a threshold
                    break; // Stop if we're running out of space
                }
                content.showTextAligned(Element.ALIGN_LEFT, lines[i], 25, yPosition, 0);
                yPosition -= lineSpacing; // Move down for the next line
            }

            content.endText();
            
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 13);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getMonthlyIncome() != null ? bill.getMonthlyIncome() : "N/A"), 160, 170, 0);
            content.endText();
            
            LocalDate billDate1 = bill.getBillDate();  // Assuming billDate is a LocalDate
            LocalDate dueDate = billDate1.plusYears(1).plusDays(7);  // Add 1 year and 7 days
            
            // Format the due date for displaying in the PDF
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            String dueDateFormatted = dueDate.format(formatter);
            
         // Set text color to red for dueDateFormatted
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);  // Set font for regular text
            content.setColorFill(BaseColor.CYAN);  // Set text color to red
            content.showTextAligned(Element.ALIGN_LEFT, dueDateFormatted, 425, 250, 0); // Adjust the coordinates as needed
            content.endText();


            
            // Close the PDF Stamper and flush the data
            stamper.close();

            // Convert generated file to ByteArrayInputStream for return
            File file = new File(outputFilePath);
            byte[] fileBytes = java.nio.file.Files.readAllBytes(file.toPath());
            return new ByteArrayInputStream(fileBytes);

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }
}
