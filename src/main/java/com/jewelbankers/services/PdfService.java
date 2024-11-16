package com.jewelbankers.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;

@Service
public class PdfService {
    
    @Autowired
    private SettingsService settingsService;
    
    @Autowired
    private ProductTypeService productTypeService;

    public ByteArrayInputStream generatePdf(Bill bill, Map<String, String> settingsMap) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);  // Set page size to A4
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);

        try {
            document.open();

            // Set up font for the content (use built-in Helvetica font)
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            Font boldFont = new Font(baseFont, 13, Font.BOLD);
            Font regularFont = new Font(baseFont, 12, Font.NORMAL);

            // Add first section with content to the PDF
            addFirstSection(document, bill,boldFont, regularFont, writer);

            // Add second section with content to the PDF
            addSecondSection(document, bill, boldFont,regularFont, writer, settingsMap);
            
         // Add third section with content to the PDF
            addThirdSection(document, bill, boldFont,regularFont, writer, settingsMap);

            document.close();  // Close the document and finish writing

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    private void addFirstSection(Document document, Bill bill, Font boldFont, Font regularFont, PdfWriter writer) throws DocumentException {
        // Get the PdfContentByte from PdfWriter for absolute positioning
        PdfContentByte content = writer.getDirectContent();

        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for Bill Serial and Bill Number
        content.setColorFill(BaseColor.BLACK);  // Set color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, 
            String.valueOf(bill.getBillSerial()) + String.valueOf(bill.getBillNo()), 50, 820, 0);  // Bill serial and number
        content.endText();
        
     // Product, Quantity, and Amount (more space)
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for product and quantity
        content.setColorFill(BaseColor.BLACK);  // Set color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, 
            "Rs. " + bill.getAmount() + "       " + 
            bill.getBillDetails().get(0).getProductDescription() + "       " + 
            String.valueOf(bill.getBillDetails().get(0).getProductQuantity()), 50, 805, 0);  // Product and quantity with spacing
        content.endText();
        
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for the date
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 50, 788, 0);  // Display the current date
        content.endText();


        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for the weight
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, String.valueOf(bill.getGrams()), 200, 788, 0);  // Display the weight (grams)
        content.endText();

        content.beginText();
        content.setFontAndSize(regularFont.getBaseFont(), 12);  // Font size 12 for the customer name
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Regular rendering mode (non-bold)
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, bill.getCustomer().getCustomerName(), 50, 773, 0);  // Display the customer name
        content.endText();
        
     // Draw a horizontal line
        content.setLineWidth(0.5f);  // Adjust the line width if needed
        content.moveTo(0, 750);   // Starting point of the line (x1, y1)
        content.lineTo(600, 750);  // Ending point of the line (x2, y2)
        content.stroke();          // Render the line

    }

    private void addSecondSection(
            Document document,
            Bill bill,
            Font boldFont,
            Font regularFont, 
            PdfWriter writer,
            Map<String, String> settingsMap
    ) throws DocumentException, IOException {
        PdfContentByte content = writer.getDirectContent();
        
        content.beginText();
        content.setFontAndSize(regularFont.getBaseFont(), 12);
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL);
        content.showTextAligned(Element.ALIGN_LEFT, "Office Copy", 30, 730, 0);
        content.endText();
        
//     // Underline "Office Copy"
//        content.moveTo(30, 728);  // Starting point of the line
//        content.lineTo(95, 728);  // Ending point of the line
//        content.stroke();
        
     // Add heading
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 16);  // Bold font for "PAWN TICKET" with font size 16
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_CENTER, "PAWN TICKET", 300, 730, 0);  // Center alignment with coordinates (300, 730)
        content.endText();

        // Draw underline below "PAWN TICKET"
        float textWidth = boldFont.getBaseFont().getWidthPoint("PAWN TICKET", 16);  // Get the width of the text at font size 16
        float startX = 300 - (textWidth / 2);  // Calculate starting X for the underline
        float endX = 300 + (textWidth / 2);    // Calculate ending X for the underline
        content.setLineWidth(0.5f);              // Set line width for the underline
        content.moveTo(startX, 725);           // Start point for the underline just below the text
        content.lineTo(endX, 725);             // End point for the underline
        content.stroke();                      // Render the line

        // Get License Number
        String licenseNumber = settingsService.getLicenceNo(); // Fetch from database
        content.beginText();
        content.setFontAndSize(regularFont.getBaseFont(), 12);
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
        content.showTextAligned(Element.ALIGN_LEFT, licenseNumber, 50, 700, 0);  // License number
        content.endText();

        // Retrieve shop details from settings map
        String shopName = settingsMap.getOrDefault("SHOP_NAME", "Shop Name");
        String shopLine1 = settingsMap.getOrDefault("SHOP_NO", "") + " " + settingsMap.getOrDefault("SHOP_STREET", "");
        String shopLine2 = settingsMap.getOrDefault("SHOP_AREA", "") + " " + settingsMap.getOrDefault("SHOP_CITY", "");
        String shopLine3 = settingsMap.getOrDefault("SHOP_STATE", "") + " - " + settingsMap.getOrDefault("SHOP_PINCODE", "");

        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 14);  // Bold font for Shop Name with font size 14
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, shopName, 50, 680, 0);  // Left alignment with coordinates (50, 665)
        content.endText();


        // Format each line before adding to the PDF
        String formattedShopLine1 = capitalizeWords(shopLine1);
        String formattedShopLine2 = capitalizeWords(shopLine2);
        String formattedShopLine3 = capitalizeWords(shopLine3);

     // Write the formatted text to PDF content (no bold, regular font)
     // Shop Address (Normal Font)
        content.beginText();
        content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
        content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine1, 50, 660, 0);  // Position at (50, 640)
        content.endText();

        content.beginText();
        content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
        content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine2, 50, 640, 0);  // Position at (50, 620)
        content.endText();

        content.beginText();
        content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
        content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine3, 50, 620, 0);  // Position at (50, 600)
        content.endText();


        // Add customer details ("To" section)
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 12);
    	content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.showTextAligned(Element.ALIGN_LEFT, "To,", 270, 705, 0);
        content.endText();

        if (bill.getCustomer() != null) {
        	content.beginText();
        	content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 14 for the customer name
        	content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        	content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        	content.setLineWidth(0.5f);  // Set line width for stroke effect
        	content.showTextAligned(Element.ALIGN_LEFT, bill.getCustomer().getCustomerName(), 290, 690, 0);  // Display the customer name
        	content.endText();


            // Customer Address (Split into lines and ensure it's not null)
            String[] addressLines = bill.getCustomer().getAddress() != null ? bill.getCustomer().getAddress().split(",") : new String[]{"Address Line 1", "Address Line 2", "Address Line 3"};
         // Customer Address (Normal Font)
            if (bill.getCustomer() != null) {
                content.beginText();
                content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 0 ? addressLines[0] : "", 290, 675, 0);  // First line of address
                content.endText();

                content.beginText();
                content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 1 ? addressLines[1] : "", 290, 660, 0);  // Second line of address
                content.endText();

                content.beginText();
                content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 2 ? addressLines[2] : "", 290, 645, 0);  // Third line of address
                content.endText();
                
                content.beginText();
                content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for the customer phone number
                content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)

                // Check if phone number is not null and not 0 before displaying
                Long phoneNo = bill.getCustomer().getPhoneno();
                if (phoneNo != null && phoneNo != 0) {
                    content.showTextAligned(Element.ALIGN_LEFT, String.valueOf(phoneNo), 290, 630, 0);  // Display phone number
                } else {
                    // Leave blank if phone number is null or 0
                    content.showTextAligned(Element.ALIGN_LEFT, "", 290, 630, 0);
                }
                content.endText();
                
             // Check if the customer has a photo and retrieve it as byte array
                byte[] customerPhoto = bill.getCustomer() != null ? bill.getCustomer().getPhoto() : null;

                if (customerPhoto != null) {
                    try {
                        // Convert the byte array to an iText Image
                        Image photo = Image.getInstance(customerPhoto);

                        // Set the position of the photo below "Pledge No" with a gap
                        photo.setAbsolutePosition(453, 555); // Adjust coordinates (x, y) as needed

                        // Extend the width and keep the height the same
                        photo.scaleToFit(130, 130); // Set width to 150 to extend, keep height at 100

                        // Add the photo to the PDF content
                        content.addImage(photo);
                    } catch (Exception e) {
                        System.out.println("Failed to add photo: " + e.getMessage());
                    }
            }
        }
        
                // Retrieve the ARTICLE_PRINT setting value
                String articlePrintSetting = settingsService.getArticlePrint();

                // Check if article photo should be printed
                if ("Y".equalsIgnoreCase(articlePrintSetting)) {
                    // Retrieve the article photo as byte array
                    byte[] articlePhoto = bill.getBillDetails().get(0).getArticlephoto() != null ? bill.getBillDetails().get(0).getArticlephoto() : null;

                    if (articlePhoto != null) {
                        try {
                            // Convert the article photo byte array to an iText Image
                            Image articleImage = Image.getInstance(articlePhoto);

                            // Set the position of the article photo below the customer photo
                            articleImage.setAbsolutePosition(453, 435); // Adjust coordinates based on layout
                            articleImage.scaleToFit(130, 130); // Set same dimensions as customer photo

                            // Add the article photo to the PDF content
                            content.addImage(articleImage);
                        } catch (Exception e) {
                            System.out.println("Failed to add article photo: " + e.getMessage());
                        }
                    } else {
                        System.out.println("No article photo available.");
                    }
                } else {
                    System.out.println("ARTICLE_PRINT setting is disabled (value: " + articlePrintSetting + "). Article photo will not be printed.");
                }
            

        // Add date and pledge number
     // Display Date
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 13 for the date label
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, "Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 450, 690, 0);  // Date aligned to left with label
        content.endText();

        // Display Pledge No
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 13 for the pledge number label
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, "Pledge No: " + bill.getBillSerial() + bill.getBillNo(), 450, 670, 0);  // Pledge No aligned to left with label
        content.endText();

     // Add Loan Amount
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 13 for the loan amount
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, "Loan Amount : " + bill.getAmount(), 40, 600, 0);  // Loan amount displayed at (40, 600)
        content.endText();

        // Convert Amount in Words to Capitalize the First Letter of Each Word
        String amountInWords = bill.getAmountInWords();
        String formattedAmountInWords = Arrays.stream(amountInWords.toLowerCase().split(" "))
                                              .filter(word -> !word.isEmpty())  // Filter out empty words
                                              .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))  // Capitalize first letter of each word
                                              .collect(Collectors.joining(" "));

        // Write the formatted amount in words to the PDF content
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for the words representation
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, "In Words: " + formattedAmountInWords, 180, 602, 0);  // Formatted words displayed at (190, 600)
        content.endText();


     // Fetch the product type code and format it to have only the first letter capitalized
        String productTypeName = productTypeService.getProductTypeCode(bill.getProductTypeNo());
        String formattedProductTypeName = productTypeName.substring(0, 1).toUpperCase() + productTypeName.substring(1).toLowerCase();

        // Write the formatted text with the product type name to the PDF content
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for the product type line
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, "The following     " + formattedProductTypeName + "     article / articles is / are pawned with us / me.", 40, 580, 0);  // Text displayed at (40, 580)
        content.endText();
        
        // Create table for the pledge details
        PdfPTable table = new PdfPTable(3); // 3 columns for item details
        table.setTotalWidth(400); // Set the total width to fit within the specified area
        table.setLockedWidth(true); // Lock width to ensure layout consistency
        table.setWidths(new int[]{1, 7, 1}); // Adjust column width ratios

        // Font for the header
        BaseFont baseFont = BaseFont.createFont();
        Font headerFont = new Font(baseFont, 12, Font.BOLD);

        // Add table headers
        PdfPCell cell = new PdfPCell(new Phrase("S.No.", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f); // Increase padding for more height
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Particulars of Pledge", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Nos", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        table.addCell(cell);

        // Font for the table data
        Font dataFont = new Font(baseFont, 12);

        int serialNo = 1;

        // Assuming bill is a single Bill object, not a collection
        Bill item = bill;  // No iteration since bill is a single object
        List<BillDetail> billDetails = item.getBillDetails();  // Assuming getBillDetails() returns a list

        if (billDetails != null && !billDetails.isEmpty()) {
            // Process the details of the single Bill
            String description = billDetails.get(0).getProductDescription();
            int quantity = billDetails.get(0).getProductQuantity();

            // Add a new row for the bill details
            PdfPCell dataCell1 = new PdfPCell(new Phrase("1", dataFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataCell1.setPadding(20f);  // Increase padding for more height
            table.addCell(dataCell1);

            PdfPCell dataCell2 = new PdfPCell(new Phrase(description, dataFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataCell2.setPadding(20f);
            table.addCell(dataCell2);

            PdfPCell dataCell3 = new PdfPCell(new Phrase(String.valueOf(quantity), dataFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataCell3.setPadding(20f);
            table.addCell(dataCell3);
        }

        // Add a large empty row for spacing before the summary row
        PdfPCell emptyRow = new PdfPCell(new Phrase(" "));
        emptyRow.setFixedHeight(30f); // Adjust height to create a moderate gap
        emptyRow.setColspan(3);  // Span across all columns
        emptyRow.setBorder(Rectangle.NO_BORDER); // No border for the empty row
        table.addCell(emptyRow);

        // Summary row (displayed closer to the note section)
        BigDecimal grams = bill.getGrams();
        BigDecimal netWeightValue = grams.subtract(BigDecimal.valueOf(0.500));
        BigDecimal netWeightDecimal = netWeightValue.setScale(3, RoundingMode.HALF_UP);
        String netWeight = netWeightDecimal.toString();
        String grossWeight = String.valueOf(bill.getGrams());
        String presentValue = String.format("%d", bill.getPresentValue());

        // Bold font for summary
        Font summaryFont = new Font(baseFont, 12, Font.BOLD);

        // Summary cell in one row, with fields aligned to the left
        PdfPCell summaryCell = new PdfPCell(new Phrase("Gross Wt : " + grossWeight + "     Nett Wt : " + netWeight + "     Value : " + presentValue, summaryFont));
        summaryCell.setColspan(3);  // Span across all 3 columns
        summaryCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        summaryCell.setBorder(Rectangle.NO_BORDER); // Remove the border line
        summaryCell.setPaddingTop(-15f); // Move the summary fields slightly higher
        summaryCell.setPaddingBottom(5f); // Adjust padding to control spacing above the note section
        table.addCell(summaryCell);

        // Position table on the page at specific coordinates (adjust coordinates as necessary)
        table.writeSelectedRows(0, -1, 40, 560, content);



     /// Date format for displaying the date as dd-MM-yyyy
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

     // Calculate the due date by adding 1 year and 7 days to the bill date
     LocalDate billDate = bill.getBillDate();  // Assuming bill.getBillDate() returns a LocalDate object
     LocalDate dueDate = billDate.plusYears(1).plusDays(7);
     String formattedDueDate = dueDate.format(dateFormatter);

     // Add note text
     content.beginText();
     content.setFontAndSize(boldFont.getBaseFont(), 10);  // Font size 10 for the note
     content.setColorFill(BaseColor.BLACK);  // Set text color to black
     content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Apply bold rendering mode
     content.setLineWidth(0.5f);  // Set line width for stroke effect

     // Display the first line of the note
     content.showTextAligned(Element.ALIGN_LEFT, "Note: Interest must be paid every 3 months. Maximum redemption period is 1 year.", 40, 430, 0);

     // Display the second line with the calculated due date
     content.showTextAligned(Element.ALIGN_LEFT, "The final due date for pledged items is only 1 year and 7 days : ",40, 415, 0);
     content.endText();
     
     // Display the date in a different color
     content.beginText();
     content.setFontAndSize(boldFont.getBaseFont(), 10);  // Font size 10 for the date
     content.setColorFill(BaseColor.MAGENTA);  // Set text color to red for the date
     content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Apply bold rendering mode

     // Display the date next to the first part of the note
     content.showTextAligned(Element.ALIGN_LEFT, formattedDueDate, 325, 415, 0);  // Adjust X position to align after the text
     content.endText();
     

     // Add signature lines - Pawn Broker
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 10);  // Font size 10 for signature line
        content.setColorFill(BaseColor.BLACK);  // Set text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Apply bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, "Signature of Pawn Broker", 40, 380, 0);  // Position at (40, 440)
        content.endText();


     // Add signature lines - Customer
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 10);  // Font size 10 for signature line
        content.setColorFill(BaseColor.BLACK);  // Set text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Apply bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, "Signature of Customer", 450, 380, 0);  // Position at (450, 440)
        content.endText();
        
     // Draw a horizontal line
        content.setLineWidth(0.5f);  // Adjust the line width if needed
        content.moveTo(0, 370);   // Starting point of the line (x1, y1)
        content.lineTo(600, 370);  // Ending point of the line (x2, y2)
        content.stroke();          // Render the line
        }      
    }

    // Helper method to capitalize only the first letter of each word
       private String capitalizeWords(String text) {
           return Arrays.stream(text.toLowerCase().split(" "))
                        .filter(word -> !word.isEmpty()) // Filter out any empty strings
                        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                        .collect(Collectors.joining(" "));
       }
       
       // Third Section 
       private void addThirdSection(
               Document document,
               Bill bill,
               Font boldFont,
               Font regularFont, 
               PdfWriter writer,
               Map<String, String> settingsMap
       ) throws DocumentException, IOException {
           PdfContentByte content = writer.getDirectContent();
           
           content.beginText();
           content.setFontAndSize(regularFont.getBaseFont(), 12);
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL);
           content.showTextAligned(Element.ALIGN_LEFT, "Customer Copy", 30, 350, 0);
           content.endText();
           
           
        // Add heading
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 16);  // Bold font for "PAWN TICKET" with font size 16
           content.setColorFill(BaseColor.BLACK);  // Set the text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           content.setLineWidth(0.5f);  // Set line width for stroke effect
           content.showTextAligned(Element.ALIGN_CENTER, "PAWN TICKET", 300, 350, 0);  // Center alignment with coordinates (300, 330)
           content.endText();

           // Draw underline below "PAWN TICKET"
           float textWidth = boldFont.getBaseFont().getWidthPoint("PAWN TICKET", 16);  // Get the width of the text at font size 16
           float startX = 300 - (textWidth / 2);  // Calculate starting X for the underline
           float endX = 300 + (textWidth / 2);    // Calculate ending X for the underline
           content.setLineWidth(0.5f);              // Set line width for the underline
           content.moveTo(startX, 345);           // Start point for the underline just below the text at y = 330
           content.lineTo(endX, 345);             // End point for the underline
           content.stroke();                      // Render the line
        
           // Get License Number
           String licenseNumber = settingsService.getLicenceNo(); // Fetch from database
           content.beginText();
           content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
           content.showTextAligned(Element.ALIGN_LEFT, licenseNumber, 50, 330, 0);  // License number
           content.endText();

           // Retrieve shop details from settings map
           String shopName = settingsMap.getOrDefault("SHOP_NAME", "Shop Name");
           String shopLine1 = settingsMap.getOrDefault("SHOP_NO", "") + " " + settingsMap.getOrDefault("SHOP_STREET", "");
           String shopLine2 = settingsMap.getOrDefault("SHOP_AREA", "") + " " + settingsMap.getOrDefault("SHOP_CITY", "");
           String shopLine3 = settingsMap.getOrDefault("SHOP_STATE", "") + " - " + settingsMap.getOrDefault("SHOP_PINCODE", "");

           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 14);  // Bold font for Shop Name with font size 14
           content.setColorFill(BaseColor.BLACK);  // Set the text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           content.setLineWidth(0.5f);  // Set line width for stroke effect
           content.showTextAligned(Element.ALIGN_LEFT, shopName, 50, 310, 0);  // Left alignment with coordinates (50, 665)
           content.endText();


           // Format each line before adding to the PDF
           String formattedShopLine1 = capitalizeWords(shopLine1);
           String formattedShopLine2 = capitalizeWords(shopLine2);
           String formattedShopLine3 = capitalizeWords(shopLine3);

        // Write the formatted text to PDF content (no bold, regular font)
        // Shop Address (Normal Font)
           content.beginText();
           content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
           content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine1, 50, 290, 0);  // Position at (50, 640)
           content.endText();

           content.beginText();
           content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
           content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine2, 50, 270, 0);  // Position at (50, 620)
           content.endText();

           content.beginText();
           content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
           content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine3, 50, 250, 0);  // Position at (50, 600)
           content.endText();


           // Add customer details ("To" section)
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 12);
       	content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           content.showTextAligned(Element.ALIGN_LEFT, "To,", 270, 330, 0);
           content.endText();

           if (bill.getCustomer() != null) {
           	content.beginText();
           	content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 14 for the customer name
           	content.setColorFill(BaseColor.BLACK);  // Set the text color to black
           	content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           	content.setLineWidth(0.5f);  // Set line width for stroke effect
           	content.showTextAligned(Element.ALIGN_LEFT, bill.getCustomer().getCustomerName(), 290, 315, 0);  // Display the customer name
           	content.endText();


               // Customer Address (Split into lines and ensure it's not null)
               String[] addressLines = bill.getCustomer().getAddress() != null ? bill.getCustomer().getAddress().split(",") : new String[]{"Address Line 1", "Address Line 2", "Address Line 3"};
            // Customer Address (Normal Font)
               if (bill.getCustomer() != null) {
                   content.beginText();
                   content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                   content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                   content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 0 ? addressLines[0] : "", 290, 300, 0);  // First line of address
                   content.endText();

                   content.beginText();
                   content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                   content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                   content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 1 ? addressLines[1] : "", 290, 285, 0);  // Second line of address
                   content.endText();

                   content.beginText();
                   content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                   content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                   content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 2 ? addressLines[2] : "", 290, 270, 0);  // Third line of address
                   content.endText();
                   
                   content.beginText();
                   content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for the customer phone number
                   content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)

                   // Check if phone number is not null and not 0 before displaying
                   Long phoneNo = bill.getCustomer().getPhoneno();
                   if (phoneNo != null && phoneNo != 0) {
                       content.showTextAligned(Element.ALIGN_LEFT, String.valueOf(phoneNo), 290, 255, 0);  // Display phone number
                   } else {
                       // Leave blank if phone number is null or 0
                       content.showTextAligned(Element.ALIGN_LEFT, "", 290, 265, 0);
                   }
                   content.endText();
                   
                // Check if the customer has a photo and retrieve it as byte array
                   byte[] customerPhoto = bill.getCustomer() != null ? bill.getCustomer().getPhoto() : null;

                   if (customerPhoto != null) {
                       try {
                           // Convert the byte array to an iText Image
                           Image photo = Image.getInstance(customerPhoto);

                           // Set the position and scale of the photo as needed
                           photo.setAbsolutePosition(453, 195); // Adjusted Y-coordinate to place below Pledge No, with some gap
                           photo.scaleToFit(130, 130); // Adjust the size to fit within 80x80 dimensions

                           // Add the photo to the PDF content
                           content.addImage(photo);
                       } catch (Exception e) {
                           System.out.println("Failed to add photo: " + e.getMessage());
                       }

               }
               }
          
               // Retrieve the ARTICLE_PRINT setting value
               String articlePrintSetting = settingsService.getArticlePrint();

               // Check if article photo should be printed
               if ("Y".equalsIgnoreCase(articlePrintSetting)) {
                   // Retrieve the article photo as byte array
                   byte[] articlePhoto = bill.getBillDetails().get(0).getArticlephoto() != null ? bill.getBillDetails().get(0).getArticlephoto() : null;

                   if (articlePhoto != null) {
                       try {
                           // Convert the article photo byte array to an iText Image
                           Image articleImage = Image.getInstance(articlePhoto);

                           // Set the position of the article photo below the customer photo
                           articleImage.setAbsolutePosition(453, 75); // Adjust coordinates based on layout
                           articleImage.scaleToFit(130, 130); // Set same dimensions as customer photo

                           // Add the article photo to the PDF content
                           content.addImage(articleImage);
                       } catch (Exception e) {
                           System.out.println("Failed to add article photo: " + e.getMessage());
                       }
                   } else {
                       System.out.println("No article photo available.");
                   }
               } else {
                   System.out.println("ARTICLE_PRINT setting is disabled (value: " + articlePrintSetting + "). Article photo will not be printed.");
               }
               
         

           // Add date and pledge number
        // Display Date
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 13 for the date label
           content.setColorFill(BaseColor.BLACK);  // Set the text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           content.setLineWidth(0.5f);  // Set line width for stroke effect
           content.showTextAligned(Element.ALIGN_LEFT, "Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 450, 330, 0);  // Date aligned to left with label
           content.endText();

           // Display Pledge No
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 13 for the pledge number label
           content.setColorFill(BaseColor.BLACK);  // Set the text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           content.setLineWidth(0.5f);  // Set line width for stroke effect
           content.showTextAligned(Element.ALIGN_LEFT, "Pledge No: " + bill.getBillSerial() + bill.getBillNo(), 450, 310, 0);  // Pledge No aligned to left with label
           content.endText();

        // Add Loan Amount
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 13 for the loan amount
           content.setColorFill(BaseColor.BLACK);  // Set the text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           content.setLineWidth(0.5f);  // Set line width for stroke effect
           content.showTextAligned(Element.ALIGN_LEFT, "Loan Amount : " + bill.getAmount(), 40, 230, 0);  // Loan amount displayed at (40, 600)
           content.endText();

           // Convert Amount in Words to Capitalize the First Letter of Each Word
           String amountInWords = bill.getAmountInWords();
           String formattedAmountInWords = Arrays.stream(amountInWords.toLowerCase().split(" "))
                                                 .filter(word -> !word.isEmpty())  // Filter out empty words
                                                 .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))  // Capitalize first letter of each word
                                                 .collect(Collectors.joining(" "));

           // Write the formatted amount in words to the PDF content
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for the words representation
           content.setColorFill(BaseColor.BLACK);  // Set the text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           content.setLineWidth(0.5f);  // Set line width for stroke effect
           content.showTextAligned(Element.ALIGN_LEFT, "In Words: " + formattedAmountInWords, 180, 232, 0);  // Formatted words displayed at (190, 600)
           content.endText();


        // Fetch the product type code and format it to have only the first letter capitalized
           String productTypeName = productTypeService.getProductTypeCode(bill.getProductTypeNo());
           String formattedProductTypeName = productTypeName.substring(0, 1).toUpperCase() + productTypeName.substring(1).toLowerCase();

           // Write the formatted text with the product type name to the PDF content
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for the product type line
           content.setColorFill(BaseColor.BLACK);  // Set the text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           content.setLineWidth(0.5f);  // Set line width for stroke effect
           content.showTextAligned(Element.ALIGN_LEFT, "The following     " + formattedProductTypeName + "     article / articles is / are pawned with us / me.", 40, 210, 0);  // Text displayed at (40, 580)
           content.endText();
           
        // Create table for the pledge details
           PdfPTable table = new PdfPTable(3); // 3 columns for item details
           table.setTotalWidth(400); // Set the total width to fit within the specified area
           table.setLockedWidth(true); // Lock width to ensure layout consistency
           table.setWidths(new int[]{1, 7, 1}); // Adjust column width ratios

           // Font for the header
           BaseFont baseFont = BaseFont.createFont();
           Font headerFont = new Font(baseFont, 12, Font.BOLD);

           // Add table headers
           PdfPCell cell = new PdfPCell(new Phrase("S.No.", headerFont));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setPadding(5f); // Increase padding for more height
           table.addCell(cell);

           cell = new PdfPCell(new Phrase("Particulars of Pledge", headerFont));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setPadding(5f);
           table.addCell(cell);

           cell = new PdfPCell(new Phrase("Nos", headerFont));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setPadding(5f);
           table.addCell(cell);

           // Font for the table data
           Font dataFont = new Font(baseFont, 12);

           int serialNo = 1;

           // Assuming bill is a single Bill object, not a collection
           Bill item = bill;  // No iteration since bill is a single object
           List<BillDetail> billDetails = item.getBillDetails();  // Assuming getBillDetails() returns a list

           if (billDetails != null && !billDetails.isEmpty()) {
               // Process the details of the single Bill
               String description = billDetails.get(0).getProductDescription();
               int quantity = billDetails.get(0).getProductQuantity();

               // Add a new row for the bill details
               PdfPCell dataCell1 = new PdfPCell(new Phrase("1", dataFont));
               cell.setHorizontalAlignment(Element.ALIGN_CENTER);
               dataCell1.setPadding(20f);  // Increase padding for more height
               table.addCell(dataCell1);

               PdfPCell dataCell2 = new PdfPCell(new Phrase(description, dataFont));
               cell.setHorizontalAlignment(Element.ALIGN_CENTER);
               dataCell2.setPadding(20f);
               table.addCell(dataCell2);

               PdfPCell dataCell3 = new PdfPCell(new Phrase(String.valueOf(quantity), dataFont));
               cell.setHorizontalAlignment(Element.ALIGN_CENTER);
               dataCell3.setPadding(20f);
               table.addCell(dataCell3);
           }

           // Add a large empty row for spacing before the summary row
           PdfPCell emptyRow = new PdfPCell(new Phrase(" "));
           emptyRow.setFixedHeight(30f); // Adjust height to create a moderate gap
           emptyRow.setColspan(3);  // Span across all columns
           emptyRow.setBorder(Rectangle.NO_BORDER); // No border for the empty row
           table.addCell(emptyRow);

           // Summary row (displayed closer to the note section)
           BigDecimal grams = bill.getGrams();
           BigDecimal netWeightValue = grams.subtract(BigDecimal.valueOf(0.500));
           BigDecimal netWeightDecimal = netWeightValue.setScale(3, RoundingMode.HALF_UP);
           String netWeight = netWeightDecimal.toString();
           String grossWeight = String.valueOf(bill.getGrams());
           String presentValue = String.format("%d", bill.getPresentValue());

           // Bold font for summary
           Font summaryFont = new Font(baseFont, 12, Font.BOLD);

           // Summary cell in one row, with fields aligned to the left
           PdfPCell summaryCell = new PdfPCell(new Phrase("Gross Wt : " + grossWeight + "     Nett Wt : " + netWeight + "     Value : " + presentValue, summaryFont));
           summaryCell.setColspan(3);  // Span across all 3 columns
           summaryCell.setHorizontalAlignment(Element.ALIGN_LEFT);
           summaryCell.setBorder(Rectangle.NO_BORDER); // Remove the border line
           summaryCell.setPaddingTop(-15f); // Move the summary fields slightly higher
           summaryCell.setPaddingBottom(5f); // Adjust padding to control spacing above the note section
           table.addCell(summaryCell);

           // Position table on the page at specific coordinates (adjust coordinates as necessary)
           table.writeSelectedRows(0, -1, 40, 190, content);




        /// Date format for displaying the date as dd-MM-yyyy
           DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Calculate the due date by adding 1 year and 7 days to the bill date
        LocalDate billDate = bill.getBillDate();  // Assuming bill.getBillDate() returns a LocalDate object
        LocalDate dueDate = billDate.plusYears(1).plusDays(7);
        String formattedDueDate = dueDate.format(dateFormatter);

        // Add note text
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 10);  // Font size 10 for the note
        content.setColorFill(BaseColor.BLACK);  // Set text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Apply bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect

        // Display the first line of the note
        content.showTextAligned(Element.ALIGN_LEFT, "Note: Interest must be paid every 3 months. Maximum redemption period is 1 year.", 40, 60, 0);

        // Display the second line with the calculated due date
        content.showTextAligned(Element.ALIGN_LEFT, "The final due date for pledged items is only 1 year and 7 days : ",40, 45, 0);
        content.endText();
        
        // Display the date in a different color
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 10);  // Font size 10 for the date
        content.setColorFill(BaseColor.MAGENTA);  // Set text color to red for the date
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Apply bold rendering mode

        // Display the date next to the first part of the note
        content.showTextAligned(Element.ALIGN_LEFT, formattedDueDate, 325, 45, 0);  // Adjust X position to align after the text
        content.endText();
        

        // Add signature lines - Pawn Broker
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 10);  // Font size 10 for signature line
           content.setColorFill(BaseColor.BLACK);  // Set text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Apply bold rendering mode
           content.setLineWidth(0.5f);  // Set line width for stroke effect
           content.showTextAligned(Element.ALIGN_LEFT, "Signature of Pawn Broker", 40, 10, 0);  // Position at (40, 440)
           content.endText();


        // Add signature lines - Customer
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 10);  // Font size 10 for signature line
           content.setColorFill(BaseColor.BLACK);  // Set text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Apply bold rendering mode
           content.setLineWidth(0.5f);  // Set line width for stroke effect
           content.showTextAligned(Element.ALIGN_LEFT, "Signature of Customer", 450, 10, 0);  // Position at (450, 440)
           content.endText();

       }
    }
}
