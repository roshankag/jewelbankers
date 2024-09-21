package com.jewelbankers.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.jewelbankers.entity.Pledge;

@Service
public class PdfService {

    private static final String TEMPLATE_PATH = "template/bill.pdf";
    private static final String OUTPUT_PATH = "bills";

   public String generateAndSaveBillPdf(Pledge pledge) throws IOException, DocumentException {
       // Ensure the output directory exists
        File outputDir = new File(OUTPUT_PATH);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Set output file path
        String outputFileName = "bill_" + pledge.getBillSerial() + "_" + pledge.getBillNo() + ".pdf";
        String outputFilePath = OUTPUT_PATH + File.separator + outputFileName;

        PdfReader reader = null;
        PdfStamper stamper = null;
        FileOutputStream fos = null;

        try {
            // Read the template PDF
            reader = new PdfReader(TEMPLATE_PATH);

            // Create a new PDF with the content filled
            fos = new FileOutputStream(outputFilePath);
            stamper = new PdfStamper(reader, fos);

            // Get the PDF content
            PdfContentByte content = stamper.getOverContent(1);

            // Set the font and size
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            content.setFontAndSize(baseFont, 12);

            // Overlay text at specific coordinates
            content.beginText();

            // Example: Placing billNo at coordinates (x, y)
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(pledge.getBillNo()), 100, 665, 0); // Adjust x, y coordinates as needed
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, pledge.getBillDate().toString(), 480, 665, 0); // Adjust x, y coordinates as needed
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, pledge.getCustomer().getCustomerName(), 250, 635, 0); // Adjust x, y coordinates as needed

            // content.showTextAligned(PdfContentByte.ALIGN_LEFT, pledge.getCustomer().getFullAddress(), 250, 625, 0); 
            // Add more fields as needed, adjusting x and y coordinates accordingly
            // content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Amount: ₹" + pledge.getAmount(), 100, 690, 0);
            ColumnText ct = new ColumnText(content);
            ct.setSimpleColumn( 50,620 ,260, 530); // Define the area: (left x, bottom y, right x, top y)

            // Set the alignment of the text, add the text, and make sure it wraps within the box
            ct.addText(new Phrase("Address: " + pledge.getCustomer().getAddress()));
            ct.setAlignment(Element.ALIGN_LEFT);
            ct.go();


            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(pledge.getGrams()), 475, 490, 0); // Adjust x, y coordinates as needed

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rs. "+ String.valueOf(pledge.getPresentValue()) , 470, 255, 0); // Adjust x, y coordinates as needed

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rs. "+ String.valueOf(pledge.getTotalAmount()) , 275, 215, 0); // Adjust x, y coordinates as needed


            content.endText();

            // Log the successful creation of the PDF
            System.out.println("PDF generated and saved at: " + outputFilePath);

            return outputFilePath;

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
