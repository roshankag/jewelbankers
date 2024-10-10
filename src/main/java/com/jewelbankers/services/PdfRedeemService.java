package com.jewelbankers.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize; // **Imported for A5 page size**
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
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
        FileOutputStream fos = null;

        try {
            String shopName = settingsMap.get("SHOP_NAME");

            String shopLine1 = settingsMap.get("SHOP_NO") + " " + settingsMap.get("SHOP_STREET");
            String shopLine2 = settingsMap.get("SHOP_AREA") + " " + settingsMap.get("SHOP_CITY");
            String shopLine3 = settingsMap.get("SHOP_STATE") + " - " + settingsMap.get("SHOP_PINCODE");

            // Read the template PDF
            reader = new PdfReader(TEMPLATE_PATH);
            
            // Create a new PDF with A5 size
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
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 13); // Bold and larger font size for the name
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillDetails().get(0).getProductDescription(), 50, 455, 0); // Adjust x, y coordinates as needed
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getBillDetails().get(0).getProductQuantity()), 366, 455, 0); // Adjust x, y coordinates as needed
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

            // Log the successful creation of the PDF
            System.out.println("PDF generated and saved at: " + outputFileName);

            // Read the generated file into a byte array
            File file = new File(outputFilePath);
            byte[] fileBytes = java.nio.file.Files.readAllBytes(file.toPath());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);

            if (fileBytes.length >= 0) {
                System.out.println("The ByteArrayOutputStream has data.");
            } else {
                System.out.println("The ByteArrayOutputStream is empty.");
            }

            return byteArrayInputStream;

        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            throw e;
        } finally {
            if (stamper != null) {
                stamper.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
}
