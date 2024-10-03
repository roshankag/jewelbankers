package com.jewelbankers.services;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
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

    private static final String TEMPLATE_PATH = "template/bill_redeem.pdf";
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

            // Create a new PDF with the content filled
            fos = new FileOutputStream(outputFilePath);
            stamper = new PdfStamper(reader, fos);

            // Get the PDF content
            PdfContentByte content = stamper.getOverContent(1);

            // Set the font and size
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            Font boldFont = new Font(baseFont, 14, Font.BOLD);
            Font regularFont = new Font(baseFont, 12, Font.NORMAL);

            // Overlay text at specific coordinates
            content.beginText();
            
            content.setFontAndSize(boldFont.getBaseFont(), 14);
            
            content.showTextAligned(Element.ALIGN_CENTER, shopName, 300, 740, 0); // Adjust x, y as needed
            
         // Shop Address (Regular Font, split into three lines)
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(Element.ALIGN_CENTER, shopLine1, 300, 725, 0); // First line
            
            content.showTextAligned(Element.ALIGN_CENTER, shopLine2, 300, 710, 0); // Second line
            
            content.showTextAligned(Element.ALIGN_CENTER, shopLine3, 300, 695, 0); // Third line
            content.endText();

            
         // Add other bill details as necessary, e.g., billNo, customer name, etc.
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 12);

            // Example: Placing billNo at coordinates (x, y)
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillRedemSerial()+" "+bill.getBillRedemNo().toString(), 145, 665, 0); // Adjust x, y coordinates as needed
            content.endText();
            
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getRedemptionDate().toString(), 440, 665, 0); // Adjust x, y coordinates as needed
            
         // Customer Name in bold
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 12); // Bold and larger font size for the name
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getCustomer().getCustomerName(), 60, 600, 0); // x=250, y=635 for name
            content.endText();
            
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 12); // Bold and larger font size for the name
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillDate().toString(), 60, 580, 0); // x=250, y=635 for name
            content.endText();
            
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 12); // Bold and larger font size for the name
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillSerial()+bill.getBillNo().toString(), 60, 560, 0); // x=250, y=635 for name
            content.endText();
            	
            
            content.showTextAligned(PdfContentByte.ALIGN_LEFT,  bill.getBillDetails().get(0).getProductDescription(), 50, 475, 0); // Adjust x, y coordinates as needed
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getBillDetails().get(0).getProductQuantity()), 370, 475, 0); // Adjust x, y coordinates as needed
           

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getGrams()), 475, 490, 0); // Adjust x, y coordinates as needed
            
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getReceivedinterest()), 475, 440, 0); // Adjust x, y coordinates as needed
            
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getInterestinmonths()), 475, 385, 0); // Adjust x, y coordinates as needed
            
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getRedemptionInterest()), 475, 330, 0); // Adjust x, y coordinates as needed
            
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getRedemptionTotal()) , 475, 265, 0); // Adjust x, y coordinates as needed
            
         // Set the font and size for the text
            content.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED), 18);

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rs. "+ String.valueOf(bill.getAmount()) , 310, 210, 0); // Adjust x, y coordinates as needed


            content.endText();
            stamper.close();

            // Log the successful creation of the PDF
            System.out.println("PDF generated and saved at: " + outputFileName);
         

         // Check if ByteArrayOutputStream is empty
            //System.out.println(fos);
            File file = new File(outputFilePath);
            byte[] fileBytes = java.nio.file.Files.readAllBytes(file.toPath());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);

            //fos.write(baos.toByteArray()); // Write the data to the file
            //fos.flush();

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