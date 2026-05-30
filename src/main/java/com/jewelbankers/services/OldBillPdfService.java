package com.jewelbankers.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
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
public class OldBillPdfService {

    @Autowired
    SettingsUtillity settingsUtillity;

    @Autowired
    SettingsService settingsService;

    private static final String TEMPLATE_PATH = "template/customercopy.pdf";
    private static final String OUTPUT_PATH = "bills";

    public ByteArrayInputStream generateOldBillPdf(Bill bill, Map<String, String> settingsMap)
            throws IOException, DocumentException {
        // Ensure the output directory exists
        File dir = new File(OUTPUT_PATH);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Set output file path
        String outputFileName = "old_bill_" + bill.getBillSerial() + "_" + bill.getBillNo() + ".pdf";
        String outputFilePath = OUTPUT_PATH + File.separator + outputFileName;

        PdfReader reader = null;
        PdfStamper stamper = null;
        FileOutputStream fos = null;

        try {
            // Ensure essential settings are not null
            String shopName = settingsMap.getOrDefault("SHOP_NAME", "Shop Name");
            String shopLine1 = settingsMap.getOrDefault("SHOP_NO", "") + " "
                    + settingsMap.getOrDefault("SHOP_STREET", "");
            String shopLine2 = settingsMap.getOrDefault("SHOP_AREA", "") + " "
                    + settingsMap.getOrDefault("SHOP_CITY", "");
            String shopLine3 = settingsMap.getOrDefault("SHOP_STATE", "") + " - "
                    + settingsMap.getOrDefault("SHOP_PINCODE", "");

            // Read the template PDF using robust loading
            java.io.InputStream templateStream = null;
            try {
                // 1. Try class loader (standard classpath lookup)
                templateStream = OldBillPdfService.class.getClassLoader().getResourceAsStream(TEMPLATE_PATH);
            } catch (Exception e) {
                // Ignore and try filesystem
            }

            if (templateStream == null) {
                // 2. Try direct file path (if running from Backend/)
                File file = new File(TEMPLATE_PATH);
                if (file.exists()) {
                    templateStream = new java.io.FileInputStream(file);
                } else {
                    // 3. Try with Backend/ prefix (if running from root folder)
                    File backendFile = new File("Backend/" + TEMPLATE_PATH);
                    if (backendFile.exists()) {
                        templateStream = new java.io.FileInputStream(backendFile);
                    }
                }
            }

            if (templateStream == null) {
                throw new java.io.IOException("Template file not found as resource or file: " + TEMPLATE_PATH);
            }

            try {
                reader = new PdfReader(templateStream);
            } finally {
                try {
                    templateStream.close();
                } catch (Exception e) {
                    // Ignore close exception
                }
            }

            // Create a new PDF with the content filled in memory
            ByteArrayOutputStream singlePageBaos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, singlePageBaos);

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
            content.setFontAndSize(boldFont.getBaseFont(), 16); // Bold font for Shop Name
            content.setColorFill(BaseColor.BLACK);
            content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
            content.setLineWidth(0.5f);
            content.showTextAligned(Element.ALIGN_CENTER, shopName, 300, 745, 0); // Center alignment with coordinates
                                                                                  // (300, 745)
            content.endText();

            // Shop Address (Regular Font, split into three lines)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(Element.ALIGN_CENTER, shopLine1, 300, 730, 0); // First line
            content.showTextAligned(Element.ALIGN_CENTER, shopLine2, 300, 715, 0); // Second line
            content.showTextAligned(Element.ALIGN_CENTER, shopLine3, 300, 700, 0); // Third line
            content.endText();

            // Bill Serial and No (Ensure they are not null)
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 13); // Use a bold font
            content.setColorFill(BaseColor.BLACK); // Set text color to black
            content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
            content.setLineWidth(0.5f); // Increase line width for more bold effect
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    bill.getBillSerial() + " " + (bill.getBillNo() != null ? bill.getBillNo().toString() : "N/A"),
                    85, 665, 0);
            content.endText();

            // Define the desired output format
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Assuming bill.getBillDate() returns a LocalDate
            LocalDate billDate = bill.getBillDate();
            String billDateFormatted;

            // Format the LocalDate object into the desired format
            if (billDate != null) {
                billDateFormatted = billDate.format(outputFormatter);
            } else {
                billDateFormatted = "N/A";
            }

            // Bill Date (Ensure it's not null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 13);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, billDateFormatted, 455, 665, 0);
            content.endText();

            // Check if the customer has a photo and retrieve it as byte array
            byte[] customerPhoto = bill.getCustomer() != null ? bill.getCustomer().getPhoto() : null;

            if (customerPhoto != null) {
                try {
                    Image photo = Image.getInstance(customerPhoto);
                    photo.setAbsolutePosition(470, 527); // Adjust coordinates (x, y) as needed
                    photo.scaleToFit(90, 1000); // Scale the image to fit within 80x80 size
                    content.addImage(photo);
                } catch (Exception e) {
                    System.out.println("Failed to add photo: " + e.getMessage());
                }
            }

            String customerName = bill.getCustomer() != null && bill.getCustomer().getCustomerName() != null
                    ? bill.getCustomer().getCustomerName()
                    : "Customer Name";
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, customerName, 35, 610, 0);
            content.endText();

            // Customer Address (Split into lines and ensure it's not null)
            String[] addressLines = bill.getCustomer() != null && bill.getCustomer().getAddress() != null
                    ? bill.getCustomer().getAddress().split(",")
                    : new String[] { "Address Line 1", "Address Line 2", "Address Line 3" };

            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines.length > 0 ? addressLines[0] : "", 35, 590,
                    0); // First line of address
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines.length > 1 ? addressLines[1] : "", 30, 570,
                    0); // Second line of address
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines.length > 2 ? addressLines[2] : "", 30, 550,
                    0); // Third line of address
            content.endText();

            // Bill Details (Ensure non-null and non-empty lists)
            if (bill.getBillDetails() != null && !bill.getBillDetails().isEmpty()) {
                content.beginText();
                content.setFontAndSize(regularFont.getBaseFont(), 14);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                        bill.getBillDetails().get(0).getProductDescription() != null
                                ? bill.getBillDetails().get(0).getProductDescription()
                                : "N/A",
                        30, 470, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                        String.valueOf(bill.getBillDetails().get(0).getProductQuantity()), 390, 470, 0);
                content.endText();
            }

            // Grams (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    String.valueOf(bill.getGrams() != null ? bill.getGrams() : "N/A"), 495, 479, 0);
            content.endText();

            // NetWt->Grams (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    String.valueOf(bill.getGrams() != null ? bill.getGrams() : "N/A"), 495, 387, 0);
            content.endText();

            // Amount Value (Ensure non-null)
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 18);
            content.setLineWidth(0.75f); // Increase the outline width
            content.setColorStroke(new BaseColor(0, 0, 0)); // Outline color (black)
            content.setColorFill(new BaseColor(0, 0, 0)); // Fill color (black)
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    "Rs. " + String.valueOf(bill.getAmount() != null ? bill.getAmount() : "N/A"), 280, 352, 0);
            content.endText();

            // AmountInWords Value (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    String.valueOf(bill.getAmountInWords() != null ? bill.getAmountInWords() : "N/A"), 30, 330, 0);
            content.endText();

            // Present Value (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    String.valueOf(bill.getPresentValue() != null ? bill.getPresentValue() : "N/A"), 495, 335, 0);
            content.endText();

            // Fetch settings from the database
            List<Settings> settingsList = settingsService.getSettings();

            // Convert the settings list to a map
            settingsMap = settingsUtillity.convertListToMap(settingsList);

            // Fetch pledge rules using the utility method
            String pledgeRules = settingsUtillity.getPledgeRules(settingsMap);

            if (pledgeRules == null) {
                pledgeRules = "The final due date for pledged items is only 1 year and 7 days: Interest must be paid once every three months without fail. The last date to redeem the pledged items: Reason for borrowing: My monthly income:";
            }

            // Manually add line breaks at appropriate places
            String[] lines = {
                    "The final due date for pledged items is only 1 year and 7 days:",
                    "Interest must be paid once every three months without fail.",
                    "The last date to redeem the pledged items:",
                    "Reason for borrowing:",
                    "My monthly income:"
            };

            float yPosition = 300;
            float lineSpacing = 20;

            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 14);
            content.setColorFill(new BaseColor(0, 0, 0));

            for (String line : lines) {
                if (yPosition < 40) {
                    break;
                }
                content.showTextAligned(Element.ALIGN_LEFT, line, 25, yPosition, 0);
                yPosition -= lineSpacing;
            }

            content.endText();

            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    bill.getBillSerial() + " " + (bill.getBillNo() != null ? bill.getBillNo().toString() : "N/A"), 147,
                    88, 0);
            content.endText();

            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, customerName, 140, 70, 0);
            content.endText();

            DateTimeFormatter outputFormatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate billDate2 = bill.getBillDate();
            String billDateFormatted1;

            if (billDate != null) {
                billDateFormatted1 = billDate2.format(outputFormatter1);
            } else {
                billDateFormatted1 = "N/A";
            }

            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, billDateFormatted1, 140, 55, 0);
            content.endText();

            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    String.valueOf(bill.getGrams() != null ? bill.getGrams() : "N/A"), 245, 55, 0);
            content.endText();

            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    "Rs. " + String.valueOf(bill.getAmount() != null ? bill.getAmount() : "N/A"), 155, 39, 0);
            content.endText();

            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    bill.getBillDetails().get(0).getProductDescription() != null
                            ? bill.getBillDetails().get(0).getProductDescription()
                            : "N/A",
                    155, 23, 0);
            content.endText();

            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 13);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,
                    String.valueOf(bill.getMonthlyIncome() != null ? bill.getMonthlyIncome() : "N/A"), 175, 220, 0);
            content.endText();

            LocalDate billDate1 = bill.getBillDate();
            LocalDate dueDate = billDate1.plusYears(1).plusDays(7);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            String dueDateFormatted = dueDate.format(formatter);

            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.setColorFill(BaseColor.CYAN);
            content.showTextAligned(Element.ALIGN_LEFT, dueDateFormatted, 425, 300, 0);
            content.endText();

            stamper.close();
            byte[] singlePageBytes = singlePageBaos.toByteArray();

            // Create a 2-page PDF by duplicating this single page twice
            ByteArrayOutputStream doublePageBaos = new ByteArrayOutputStream();
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfCopy copy = new com.itextpdf.text.pdf.PdfCopy(doc, doublePageBaos);
            doc.open();

            // Load the generated single-page PDF
            PdfReader singleReader = new PdfReader(singlePageBytes);
            com.itextpdf.text.pdf.PdfImportedPage page = copy.getImportedPage(singleReader, 1);

            // Add the same page twice
            copy.addPage(page);
            copy.addPage(page);

            doc.close();
            singleReader.close();

            // Write the final 2-page PDF to outputFilePath
            byte[] finalBytes = doublePageBaos.toByteArray();
            fos = new FileOutputStream(outputFilePath);
            fos.write(finalBytes);
            fos.flush();
            fos.close();
            fos = null;

            return new ByteArrayInputStream(finalBytes);

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
