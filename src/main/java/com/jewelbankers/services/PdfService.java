package com.jewelbankers.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
    
    //First Section
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
     // Assuming you have a Bill object with details
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for product and quantity
        content.setColorFill(BaseColor.BLACK);  // Set color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect

        // Calculate total quantity from all BillDetails
        int totalQuantity = 0;
        for (BillDetail detail : bill.getBillDetails()) {
            totalQuantity += detail.getProductQuantity();
        }

        // Convert the total quantity to words
        String quantityInWords = convertNumberToWords(totalQuantity);

        // Determine the product description based on total quantity
        String productDescriptionText;
        if (totalQuantity == 1) {
            // Display full description if quantity is 1
            productDescriptionText = bill.getBillDetails().get(0).getProductDescription();
        } else {
            // Display consolidated description if quantity is 2 or more
            productDescriptionText = quantityInWords + " Gold Articles"; // e.g., "Sixteen Gold Articles"
        }

        // Create the display text
        String displayText = "Rs. " + bill.getAmount() + "       " + productDescriptionText;

        // Display the text on the PDF
        content.showTextAligned(Element.ALIGN_LEFT, displayText, 50, 805, 0);  // Adjust the position as needed
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
    
    //Second Section
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
        content.setFontAndSize(regularFont.getBaseFont(), 10);
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL);
        content.showTextAligned(Element.ALIGN_LEFT, "Office Copy", 20, 735, 0);
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
        content.showTextAligned(Element.ALIGN_LEFT, licenseNumber, 50, 720, 0);  // License number
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
        content.showTextAligned(Element.ALIGN_LEFT, shopName, 50, 700, 0);  // Left alignment with coordinates (50, 665)
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
        content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine1, 50, 680, 0);  // Position at (50, 640)
        content.endText();

        content.beginText();
        content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
        content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine2, 50, 660, 0);  // Position at (50, 620)
        content.endText();

        content.beginText();
        content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
        content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine3, 50, 640, 0);  // Position at (50, 600)
        content.endText();


        // Add customer details ("To" section)
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 12);
    	content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.showTextAligned(Element.ALIGN_LEFT, "To,", 240, 710, 0);
        content.endText();

        if (bill.getCustomer() != null) {
            // Prepare the string with customer name and phone number
            String customerDetails = bill.getCustomer().getCustomerName(); // Get customer name
            Long phoneNo = bill.getCustomer().getPhoneno();               // Get phone number
            
            // Append phone number if it's not null or 0
            if (phoneNo != null && phoneNo != 0) {
                customerDetails += "  -  " + phoneNo;
            }
            
            // Set up the text rendering
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 13);            // Font size 13
            content.setColorFill(BaseColor.BLACK);                         // Text color: black
            content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
            content.setLineWidth(0.5f);                                    // Set line width for stroke effect
            
            // Display the concatenated customer name and phone number
            content.showTextAligned(Element.ALIGN_LEFT, customerDetails, 250, 695, 0); // Adjust Y-coordinate as needed
            content.endText();
        



            // Customer Address (Split into lines and ensure it's not null)
            String[] addressLines = bill.getCustomer().getAddress() != null ? bill.getCustomer().getAddress().split(",") : new String[]{"Address Line 1", "Address Line 2", "Address Line 3"};
         // Customer Address (Normal Font)
            if (bill.getCustomer() != null) {
                content.beginText();
                content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 0 ? addressLines[0] : "", 250, 680, 0);  // First line of address
                content.endText();

                content.beginText();
                content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 1 ? addressLines[1] : "", 250, 665, 0);  // Second line of address
                content.endText();

                content.beginText();
                content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 2 ? addressLines[2] : "", 250, 650, 0);  // Third line of address
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
        content.showTextAligned(Element.ALIGN_LEFT, "Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 450, 680, 0);  // Date aligned to left with label
        content.endText();

     // Display Pledge No
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 13 for the pledge number label
        content.setColorFill(BaseColor.BLACK);              // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);                         // Set line width for stroke effect

        // Format Pledge No with space between serial and number
        String pledgeNo = bill.getBillSerial() + " " + bill.getBillNo(); 

        content.showTextAligned(Element.ALIGN_LEFT, "Pledge No: " + pledgeNo, 450, 660, 0);  // Pledge No aligned to left with label
        content.endText();


     // Add Loan Amount
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 13 for the loan amount
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, "Loan Amount : " + bill.getAmount(), 40, 620, 0);  // Loan amount displayed at (40, 600)
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
        content.showTextAligned(Element.ALIGN_LEFT, "In Words: " + formattedAmountInWords, 180, 620, 0);  // Formatted words displayed at (190, 600)
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
        content.showTextAligned(Element.ALIGN_LEFT, "The following     " + formattedProductTypeName + "     article / articles is / are pawned with us / me.", 40, 600, 0);  // Text displayed at (40, 580)
        content.endText();
        
     // Write the formatted text with the product type name to the PDF content
        content.beginText();
        content.setFontAndSize(boldFont.getBaseFont(), 12);  // Font size 12 for the product type line
        content.setColorFill(BaseColor.BLACK);  // Set the text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect
        content.showTextAligned(Element.ALIGN_LEFT, "The following     " + formattedProductTypeName + "     article / articles is / are pawned with us / me.", 40, 600, 0);  // Text displayed at (40, 600)
        content.endText();

        // Adjust the table position to render just below the text
        float tableYPosition = 590; // Adjust the Y position of the table relative to the text

        // Create the table for the pledge details
        PdfPTable table = new PdfPTable(3); // 3 columns for item details
        table.setTotalWidth(400); // Set the total width to fit within the specified area
        table.setLockedWidth(true); // Lock width to ensure layout consistency
        table.setWidths(new int[]{1, 4, 1}); // Adjust column width ratios

        // Font for the header
        BaseFont baseFont = BaseFont.createFont();
        Font headerFont = new Font(baseFont, 12, Font.BOLD);

        // Add table headers
        PdfPCell cell = new PdfPCell(new Phrase("S.No.", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(4f); // Increase padding for more height
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Particulars of Pledge", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(4f);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Nos", headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(4f);
        table.addCell(cell);

        // Font for the table data
        Font dataFont = new Font(baseFont, 10);

        // Assuming bill is a single Bill object
        Bill item = bill;
        List<BillDetail> billDetails = item.getBillDetails(); // Assuming getBillDetails() returns a list

        // Check if there are items in the billDetails list
        if (billDetails != null && !billDetails.isEmpty()) {
            int serialNo = 1; // Initialize serial number

            for (BillDetail detail : billDetails) {
                // Fetch data for each row
                String description = detail.getProductDescription(); // Dynamically fetched product description
                int quantity = detail.getProductQuantity(); // Dynamically fetched product quantity

                // Add a new row for each product
                PdfPCell dataCell1 = new PdfPCell(new Phrase(String.valueOf(serialNo), dataFont));
                dataCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                dataCell1.setPadding(5f);
                table.addCell(dataCell1);

                PdfPCell dataCell2 = new PdfPCell(new Phrase(description, dataFont));
                dataCell2.setHorizontalAlignment(Element.ALIGN_LEFT); // Align left for better readability
                dataCell2.setPadding(5f);
                table.addCell(dataCell2);

                PdfPCell dataCell3 = new PdfPCell(new Phrase(String.valueOf(quantity), dataFont));
                dataCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                dataCell3.setPadding(5f);
                table.addCell(dataCell3);

                serialNo++; // Increment serial number for the next row
            }
        } else {
            // Add a placeholder row if no details are available
            PdfPCell placeholderCell = new PdfPCell(new Phrase("No items available", dataFont));
            placeholderCell.setColspan(3);
            placeholderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            placeholderCell.setPadding(5f);
            table.addCell(placeholderCell);
        }

     // Write the table directly below the text
        table.writeSelectedRows(0, -1, 40, tableYPosition, content);
        
     // Calculate Net Weight as Gross Weight - 0.500
        BigDecimal grossWeightDecimal = bill.getGrams();
        BigDecimal netWeightValue = grossWeightDecimal.subtract(BigDecimal.valueOf(0.500));
        BigDecimal netWeightDecimal = netWeightValue.setScale(3, RoundingMode.HALF_UP); // Round to 3 decimal places

        String grossWeight = grossWeightDecimal.toString(); // Convert to String
        String netWeight = netWeightDecimal.toString(); // Convert to String
        String presentValue = String.format("%d", bill.getPresentValue()); // Present Value as String

        // Begin text for custom fields after the table
        content.beginText();

        // Font and size for custom fields
        content.setFontAndSize(boldFont.getBaseFont(), 12); // Use a bold font size 10
        content.setColorFill(BaseColor.BLACK); // Set text color to black
        content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Apply bold rendering mode
        content.setLineWidth(0.5f);  // Set line width for stroke effect

        // Display "Gross Wt"
        content.showTextAligned(Element.ALIGN_LEFT, "Gross Wt : " + grossWeight, 40, 445, 0); // Adjust X, Y coordinates as per the layout

        // Display "Nett Wt"
        content.showTextAligned(Element.ALIGN_LEFT, "Net Wt : " + netWeight, 200, 445, 0); // Adjust X coordinate to position correctly next to "Gross Wt"

        // Display "Value"
        content.showTextAligned(Element.ALIGN_LEFT, "Present Value : " + presentValue, 330, 445, 0); // Adjust X coordinate for correct alignment

        content.endText();


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
           content.setFontAndSize(regularFont.getBaseFont(), 10);
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL);
           content.showTextAligned(Element.ALIGN_LEFT, "Customer Copy", 20, 355, 0);
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
           content.showTextAligned(Element.ALIGN_LEFT, licenseNumber, 50, 340, 0);  // License number
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
           content.showTextAligned(Element.ALIGN_LEFT, shopName, 50, 325, 0);  // Left alignment with coordinates (50, 665)
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
           content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine1, 50, 305, 0);  // Position at (50, 640)
           content.endText();

           content.beginText();
           content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
           content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine2, 50, 285, 0);  // Position at (50, 620)
           content.endText();

           content.beginText();
           content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
           content.showTextAligned(Element.ALIGN_LEFT, formattedShopLine3, 50, 265, 0);  // Position at (50, 600)
           content.endText();


           // Add customer details ("To" section)
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 12);
       	content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           content.showTextAligned(Element.ALIGN_LEFT, "To,", 240, 330, 0);
           content.endText();

           if (bill.getCustomer() != null) {
        	    // Prepare the string with customer name and phone number
        	    String customerDetails = bill.getCustomer().getCustomerName();
        	    Long phoneNo = bill.getCustomer().getPhoneno();
        	    if (phoneNo != null && phoneNo != 0) {
        	        customerDetails += "  -  " + phoneNo;
        	    }
        	    
        	    // Set up the text rendering
        	    content.beginText();
        	    content.setFontAndSize(boldFont.getBaseFont(), 13); // Font size 13 for the details
        	    content.setColorFill(BaseColor.BLACK);             // Set the text color to black
        	    content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
        	    content.setLineWidth(0.5f);                        // Set line width for stroke effect

        	    // Display the concatenated customer details
        	    content.showTextAligned(Element.ALIGN_LEFT, customerDetails, 250, 315, 0);
        	    content.endText();

               // Customer Address (Split into lines and ensure it's not null)
               String[] addressLines = bill.getCustomer().getAddress() != null ? bill.getCustomer().getAddress().split(",") : new String[]{"Address Line 1", "Address Line 2", "Address Line 3"};
            // Customer Address (Normal Font)
               if (bill.getCustomer() != null) {
                   content.beginText();
                   content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                   content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                   content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 0 ? addressLines[0] : "", 250, 300, 0);  // First line of address
                   content.endText();

                   content.beginText();
                   content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                   content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                   content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 1 ? addressLines[1] : "", 250, 285, 0);  // Second line of address
                   content.endText();

                   content.beginText();
                   content.setFontAndSize(regularFont.getBaseFont(), 12);  // Regular font with size 12
                   content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL); // Regular rendering mode (no bold)
                   content.showTextAligned(Element.ALIGN_LEFT, addressLines.length > 2 ? addressLines[2] : "", 250, 270, 0);  // Third line of address
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
           content.showTextAligned(Element.ALIGN_LEFT, "Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 450, 340, 0);  // Date aligned to left with label
           content.endText();

        // Display Pledge No
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 13 for the pledge number label
           content.setColorFill(BaseColor.BLACK);              // Set the text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           content.setLineWidth(0.5f);                         // Set line width for stroke effect

           // Format Pledge No with space between serial and number
           String pledgeNo = bill.getBillSerial() + " " + bill.getBillNo(); 

           content.showTextAligned(Element.ALIGN_LEFT, "Pledge No: " + pledgeNo, 450, 300, 0);  // Pledge No aligned to left with label
           content.endText();


        // Add Loan Amount
           content.beginText();
           content.setFontAndSize(boldFont.getBaseFont(), 13);  // Font size 13 for the loan amount
           content.setColorFill(BaseColor.BLACK);  // Set the text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
           content.setLineWidth(0.5f);  // Set line width for stroke effect
           content.showTextAligned(Element.ALIGN_LEFT, "Loan Amount : " + bill.getAmount(), 40, 245, 0);  // Loan amount displayed at (40, 600)
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
           content.showTextAligned(Element.ALIGN_LEFT, "In Words: " + formattedAmountInWords, 180, 245, 0);  // Formatted words displayed at (190, 600)
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
           content.showTextAligned(Element.ALIGN_LEFT, "The following     " + formattedProductTypeName + "     article / articles is / are pawned with us / me.", 40, 225, 0);  // Text displayed at (40, 580)
           content.endText();
           
        // Adjust the table position to render just below the text
           float tableYPosition = 220; // Adjust the Y position of the table relative to the text

           // Create the table for the pledge details
           PdfPTable table = new PdfPTable(3); // 3 columns for item details
           table.setTotalWidth(400); // Set the total width to fit within the specified area
           table.setLockedWidth(true); // Lock width to ensure layout consistency
           table.setWidths(new int[]{1, 4, 1}); // Adjust column width ratios

           // Font for the header
           BaseFont baseFont = BaseFont.createFont();
           Font headerFont = new Font(baseFont, 12, Font.BOLD);

           // Add table headers
           PdfPCell cell = new PdfPCell(new Phrase("S.No.", headerFont));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setPadding(4f); // Increase padding for more height
           table.addCell(cell);

           cell = new PdfPCell(new Phrase("Particulars of Pledge", headerFont));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setPadding(4f);
           table.addCell(cell);

           cell = new PdfPCell(new Phrase("Nos", headerFont));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setPadding(4f);
           table.addCell(cell);

           // Font for the table data
           Font dataFont = new Font(baseFont, 10);

           // Assuming bill is a single Bill object
           Bill item = bill;
           List<BillDetail> billDetails = item.getBillDetails(); // Assuming getBillDetails() returns a list

           // Check if there are items in the billDetails list
           if (billDetails != null && !billDetails.isEmpty()) {
               int serialNo = 1; // Initialize serial number

               for (BillDetail detail : billDetails) {
                   // Fetch data for each row
                   String description = detail.getProductDescription(); // Dynamically fetched product description
                   int quantity = detail.getProductQuantity(); // Dynamically fetched product quantity

                   // Add a new row for each product
                   PdfPCell dataCell1 = new PdfPCell(new Phrase(String.valueOf(serialNo), dataFont));
                   dataCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                   dataCell1.setPadding(5f);
                   table.addCell(dataCell1);

                   PdfPCell dataCell2 = new PdfPCell(new Phrase(description, dataFont));
                   dataCell2.setHorizontalAlignment(Element.ALIGN_LEFT); // Align left for better readability
                   dataCell2.setPadding(5f);
                   table.addCell(dataCell2);

                   PdfPCell dataCell3 = new PdfPCell(new Phrase(String.valueOf(quantity), dataFont));
                   dataCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                   dataCell3.setPadding(5f);
                   table.addCell(dataCell3);

                   serialNo++; // Increment serial number for the next row
               }
           } else {
               // Add a placeholder row if no details are available
               PdfPCell placeholderCell = new PdfPCell(new Phrase("No items available", dataFont));
               placeholderCell.setColspan(3);
               placeholderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
               placeholderCell.setPadding(5f);
               table.addCell(placeholderCell);
           }

        // Write the table directly below the text
           table.writeSelectedRows(0, -1, 40, tableYPosition, content);
           
        // Calculate Net Weight as Gross Weight - 0.500
           BigDecimal grossWeightDecimal = bill.getGrams();
           BigDecimal netWeightValue = grossWeightDecimal.subtract(BigDecimal.valueOf(0.500));
           BigDecimal netWeightDecimal = netWeightValue.setScale(3, RoundingMode.HALF_UP); // Round to 3 decimal places

           String grossWeight = grossWeightDecimal.toString(); // Convert to String
           String netWeight = netWeightDecimal.toString(); // Convert to String
           String presentValue = String.format("%d", bill.getPresentValue()); // Present Value as String

           // Begin text for custom fields after the table
           content.beginText();

           // Font and size for custom fields
           content.setFontAndSize(boldFont.getBaseFont(), 12); // Use a bold font size 10
           content.setColorFill(BaseColor.BLACK); // Set text color to black
           content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Apply bold rendering mode
           content.setLineWidth(0.5f);  // Set line width for stroke effect

           // Display "Gross Wt"
           content.showTextAligned(Element.ALIGN_LEFT, "Gross Wt : " + grossWeight, 40, 75, 0); // Adjust X, Y coordinates as per the layout

           // Display "Nett Wt"
           content.showTextAligned(Element.ALIGN_LEFT, "Net Wt : " + netWeight, 200, 75, 0); // Adjust X coordinate to position correctly next to "Gross Wt"

           // Display "Value"
           content.showTextAligned(Element.ALIGN_LEFT, "Present Value : " + presentValue, 330, 75, 0); // Adjust X coordinate for correct alignment

           content.endText();





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
       /**
        * Converts a number to words (e.g., 17 -> "Seventeen").
        * This is a utility function for handling quantities in words.
        */
       private String convertNumberToWords(int number) {
    	    String[] words = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
    	                      "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    	    String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

    	    if (number < 20) {
    	        return words[number];
    	    } else if (number < 100) {
    	        return tens[number / 10] + (number % 10 > 0 ? " " + words[number % 10] : "");
    	    } else {
    	        return "More than Ninety-Nine";  // Customize for larger numbers
    	    }
       } 
}