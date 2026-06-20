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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize; // **Imported for A5 page size**
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.jewelbankers.Utility.SettingsUtillity;
import com.jewelbankers.entity.Bill;

@Service
public class PdfRedeemService {
    
    @Autowired
    SettingsUtillity settingsUtillity;

    private static final String TEMPLATE_PATH = "template/redeem.pdf";
    private static final String OUTPUT_PATH = "bills";

    public ByteArrayInputStream generateAndSaveRedeemBillPdf(Bill bill, Map<String, String> settingsMap) throws IOException, DocumentException {
        // Ensure the output directory exists
        File dir = new File(OUTPUT_PATH);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Set output file path
        String outputFileName = "bill_" + bill.getBillRedemSerial() + "_" + bill.getBillRedemNo() + ".pdf";
        String outputFilePath = OUTPUT_PATH + File.separator + outputFileName;

        System.out.println(outputFilePath);

        PdfReader reader = null;
        PdfStamper stamper = null;
        // Stamp into memory instead of directly to file so we can flatten afterwards
        ByteArrayOutputStream stampBaos = new ByteArrayOutputStream();

        try {
            String shopName = settingsMap.get("SHOP_NAME");

            String shopLine1 = settingsMap.get("SHOP_NO") + " " + settingsMap.get("SHOP_STREET");
            String shopLine2 = settingsMap.get("SHOP_AREA") + " " + settingsMap.get("SHOP_CITY");
            String shopLine3 = settingsMap.get("SHOP_STATE") + " - " + settingsMap.get("SHOP_PINCODE");

            // Read the template PDF
            reader = new PdfReader(TEMPLATE_PATH);

            // Stamp into an in-memory buffer (not directly to file)
            stamper = new PdfStamper(reader, stampBaos);

            // Set the page size to A5
            Rectangle a5Size = PageSize.A5;
            stamper.getWriter().setPageSize(a5Size);

            // Get the PDF content
            PdfContentByte content = stamper.getOverContent(1);

            // Set the font and size
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            Font boldFont = new Font(baseFont, 14, Font.BOLD);
            Font regularFont = new Font(baseFont, 12, Font.NORMAL);

            // Overlay text at specific coordinates
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 16);  // Bold font for Shop Name, you can adjust the font size as needed
            content.setColorFill(BaseColor.BLACK);
            content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); // Bold rendering mode
            content.setLineWidth(0.5f);
            content.showTextAligned(Element.ALIGN_CENTER, shopName, 290, 727, 0); // Centered on A5
            content.endText();

            // Shop Address (Regular Font, split into three lines)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(Element.ALIGN_CENTER, shopLine1, 290, 710, 0); // First line
            content.showTextAligned(Element.ALIGN_CENTER, shopLine2, 290, 695, 0); // Second line
            content.showTextAligned(Element.ALIGN_CENTER, shopLine3, 290, 680, 0); // Third line
            content.endText();

            // Add other bill details as necessary, e.g., billNo, customer name, etc.
            content.beginText();
            // Set font to bold, size to 14, and make text black
            content.setFontAndSize(boldFont.getBaseFont(), 13);  // Use a bold font
            content.setColorFill(BaseColor.BLACK);  // Set text color 
            content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
            content.setLineWidth(0.5f); 
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillRedemSerial() + " " + bill.getBillRedemNo().toString(), 152, 651, 0); // Adjust x, y coordinates as needed
            content.endText();

         // Define the desired output format
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Desired output format

            // Assuming bill.getRedemptionDate() returns a LocalDate
            LocalDate redemptionDate = bill.getRedemptionDate(); // This should return the date as a LocalDate
            String redemptionDateFormatted;

            // Format the LocalDate object into the desired format
            if (redemptionDate != null) {
                redemptionDateFormatted = redemptionDate.format(outputFormatter);
            } else {
                redemptionDateFormatted = "N/A"; // Default value if the redemption date is null
            }

            // Displaying the redemption date
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, redemptionDateFormatted, 437, 651, 0); // Adjust x, y coordinates as needed
            content.endText();
            
         // Check if the customer has a photo and retrieve it as byte array
            byte[] customerPhoto = bill.getCustomer() != null ? bill.getCustomer().getPhoto() : null;
            
            if (customerPhoto != null) {
                try {
                    // Convert the byte array to an iText Image
                    Image photo = Image.getInstance(customerPhoto);

                    // Set the position and scale of the photo as needed
                    photo.setAbsolutePosition(450, 520); // Adjust coordinates (x, y) as needed
                    photo.scaleToFit(130, 130); // Scale the image to fit within 80x80 size

                    // Add the photo to the PDF content
                    content.addImage(photo);
                } catch (Exception e) {
                    System.out.println("Failed to add photo: " + e.getMessage());
                }
            }
            
            
            // Customer Name in bold
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 12); // Bold and larger font size for the name
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getCustomer().getCustomerName(), 45, 605, 0); // x=50, y=300 for name
            content.endText();
            
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12); // Bold and larger font size for the name
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getCustomer().getCustomerName(), 115, 587, 0); // x=50, y=300 for name
            content.endText();

         // Define the desired output format
            DateTimeFormatter outputFormatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Desired output format

            // Assuming bill.getBillDate() returns a LocalDate
            LocalDate billDate = bill.getBillDate(); // This should return the date as a LocalDate
            String billDateFormatted;

            // Format the LocalDate object into the desired format
            if (billDate != null) {
                billDateFormatted = billDate.format(outputFormatter1);
            } else {
                billDateFormatted = "N/A"; // Default value if the bill date is null
            }

            // Displaying the bill date
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 13); // Bold and larger font size for the name
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, billDateFormatted, 115, 567, 0); // Adjust x, y coordinates as needed
            content.endText();

            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 13); // Bold and larger font size for the name
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillSerial() + " " + bill.getBillNo().toString(), 86, 547, 0); // Adding space between billSerial and billNo
            content.endText();


            // Additional bill details
         // Define the maximum width for the text and line height
            int maxWidth = 300; // Adjust the width to fit your PDF layout
            int lineHeight = 15; // Adjust the line height for spacing

            // Get the product description
            String productDescription = bill.getBillDetails().get(0).getProductDescription();

            // Split the text into multiple lines
            List<String> lines = splitTextIntoLines(productDescription, maxWidth, boldFont.getBaseFont(), 13);

            // Start writing text
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 13); // Set the font and size

            int yCoordinate = 455; // Starting y-coordinate
            for (String line : lines) {
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, line, 40, yCoordinate, 0); // Adjust x as needed
                yCoordinate -= lineHeight; // Move to the next line
            }
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getBillDetails().get(0).getProductQuantity()), 366, 455, 0); // Adjust coordinates
            content.endText();

            
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 13); 
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getGrams()), 470, 455, 0); // Adjust x, y coordinates as needed
            content.endText();
            
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 13); // Bold and larger font size for the name
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getAmount()), 470, 405, 0); // Adjust x, y coordinates as needed
            content.endText();
            
            stamper.close();
            stamper = null;

            // ── FLATTEN PASS ─────────────────────────────────────────────────────
            // Re-render the stamped PDF through a plain PdfWriter so that the base
            // template layer and the overlay text layer are merged into ONE content
            // stream. Without this, some browsers/printers skip the overlay text.
            byte[] stampedBytes = stampBaos.toByteArray();
            ByteArrayOutputStream flatBaos = new ByteArrayOutputStream();
            PdfReader flatReader = new PdfReader(stampedBytes);
            com.itextpdf.text.Document flatDoc =
                    new com.itextpdf.text.Document(flatReader.getPageSizeWithRotation(1));
            com.itextpdf.text.pdf.PdfWriter flatWriter =
                    com.itextpdf.text.pdf.PdfWriter.getInstance(flatDoc, flatBaos);
            flatDoc.open();
            com.itextpdf.text.pdf.PdfContentByte flatCb = flatWriter.getDirectContent();
            com.itextpdf.text.pdf.PdfImportedPage flatPage =
                    flatWriter.getImportedPage(flatReader, 1);
            flatCb.addTemplate(flatPage, 0, 0);
            flatDoc.close();
            flatReader.close();
            byte[] flatBytes = flatBaos.toByteArray();
            // ─────────────────────────────────────────────────────────────────────

            // Write the flattened bytes to the output file
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.write(flatBytes);
            }

            System.out.println("PDF generated and saved at: " + outputFileName);
            return new ByteArrayInputStream(flatBytes);

        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            throw e;
        } finally {
            if (stamper != null) {
                try { stamper.close(); } catch (Exception ignored) {}
            }
            if (reader != null) {
                reader.close();
            }
        }
    }
    
    private List<String> splitTextIntoLines(String text, int maxWidth, BaseFont font, int fontSize) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String testLine = line + (line.length() == 0 ? "" : " ") + word;
            float width = font.getWidthPoint(testLine, fontSize);

            if (width > maxWidth) {
                lines.add(line.toString()); // Add the current line to the list
                line = new StringBuilder(word); // Start a new line with the current word
            } else {
                line.append((line.length() == 0 ? "" : " ") + word);
            }
        }

        // Add the last line
        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines;
    }

}