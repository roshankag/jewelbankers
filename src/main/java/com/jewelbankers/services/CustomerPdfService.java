//package com.jewelbankers.services;
//
//
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.jewelbankers.Utility.SettingsUtillity;
//import com.jewelbankers.entity.Bill;
//
//@Service
//public class PdfService {
//	
//	@Autowired
//	SettingsUtillity settingsUtillity;
//
//	private static final String CUSTOMER_TEMPLATE_PATH = "template/customercopy.pdf"; // Updated template path for customer copy
//    private static final String OFFICE_TEMPLATE_PATH = "template/officecopy.pdf"; // Updated template path for office copy
//    private static final String OUTPUT_PATH = "bills";
//
//    public ByteArrayInputStream[] generateAndSaveBillPdf(Bill bill, Map<String, String> settingsMap) throws IOException, DocumentException {
//        // Ensure the output directory exists
//        File dir = new File(OUTPUT_PATH);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        // Set output file names for customer and office copies
//        String customerOutputFileName = "customer_bill_" + bill.getBillSerial() + "_" + bill.getBillNo() + ".pdf";
//        String officeOutputFileName = "office_bill_" + bill.getBillSerial() + "_" + bill.getBillNo() + ".pdf";
//
//        String customerOutputFilePath = OUTPUT_PATH + File.separator + customerOutputFileName;
//        String officeOutputFilePath = OUTPUT_PATH + File.separator + officeOutputFileName;
//
//        System.out.println("Customer PDF Path: " + customerOutputFilePath);
//        System.out.println("Office PDF Path: " + officeOutputFilePath);
//
//        // Create an array to hold both ByteArrayInputStream objects
//        ByteArrayInputStream[] byteArrayInputs = new ByteArrayInputStream[2];
//
//        try {
//            // Generate the customer copy PDF
//            generatePdfFromTemplate(CUSTOMER_TEMPLATE_PATH, customerOutputFilePath, bill, settingsMap);
//            
//            // Generate the office copy PDF
//            generatePdfFromTemplate(OFFICE_TEMPLATE_PATH, officeOutputFilePath, bill, settingsMap);
//
//            // Read both generated PDF files into ByteArrayInputStream
//            byteArrayInputs[0] = new ByteArrayInputStream(java.nio.file.Files.readAllBytes(new File(customerOutputFilePath).toPath()));
//            byteArrayInputs[1] = new ByteArrayInputStream(java.nio.file.Files.readAllBytes(new File(officeOutputFilePath).toPath()));
//
//            // Log successful creation of both PDFs
//            System.out.println("PDFs generated and saved at: " + customerOutputFileName + " and " + officeOutputFileName);
//
//            // Optional: Check if both ByteArrayInputStreams have data
//            if (byteArrayInputs[0].available() > 0) {
//                System.out.println("The ByteArrayInputStream for customer PDF has data.");
//            } else {
//                System.out.println("The ByteArrayInputStream for customer PDF is empty.");
//            }
//
//            if (byteArrayInputs[1].available() > 0) {
//                System.out.println("The ByteArrayInputStream for office PDF has data.");
//            } else {
//                System.out.println("The ByteArrayInputStream for office PDF is empty.");
//            }
//
//            return byteArrayInputs; // Return the array of PDF streams
//
//        } catch (DocumentException | IOException e) {
//            System.err.println("Error generating PDFs: " + e.getMessage());
//            throw e;
//        }
//    }
//
//   
//   private void generatePdfFromTemplate(String templatePath, String outputFilePath, Bill bill, Map<String, String> settingsMap) throws IOException, DocumentException {
//        
//        PdfReader reader = null;
//        PdfStamper stamper = null;
//        FileOutputStream fos = null;
//
//        try {
//        	
//        	String shopName = settingsMap.get("SHOP_NAME");
//        	
//        	 String shopLine1 = settingsMap.get("SHOP_NO") + " " + settingsMap.get("SHOP_STREET");
//             String shopLine2 = settingsMap.get("SHOP_AREA") + " " + settingsMap.get("SHOP_CITY");
//             String shopLine3 = settingsMap.get("SHOP_STATE") + " - " + settingsMap.get("SHOP_PINCODE");
//             	
//            // Read the template PDF
//            reader = new PdfReader(templatePath);
//
//            // Create a new PDF with the content filled
//            fos = new FileOutputStream(outputFilePath);
//            stamper = new PdfStamper(reader, fos);
//
//            // Get the PDF content
//            PdfContentByte content = stamper.getOverContent(1);
//
//            // Set the font and size
//            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
//            Font boldFont = new Font(baseFont, 14, Font.BOLD);
//            Font regularFont = new Font(baseFont, 12, Font.NORMAL);
//
//            // Overlay text at specific coordinates
//            content.beginText();
//            
//            content.setFontAndSize(boldFont.getBaseFont(), 14);
//            
//            content.showTextAligned(Element.ALIGN_CENTER, shopName, 300, 740, 0); // Adjust x, y as needed
//            
//         // Shop Address (Regular Font, split into three lines)
//            content.setFontAndSize(regularFont.getBaseFont(), 12);
//            content.showTextAligned(Element.ALIGN_CENTER, shopLine1, 300, 725, 0); // First line
//            
//            content.showTextAligned(Element.ALIGN_CENTER, shopLine2, 300, 710, 0); // Second line
//            
//            content.showTextAligned(Element.ALIGN_CENTER, shopLine3, 300, 695, 0); // Third line
//            content.endText();
//
//            
//         // Add other bill details as necessary, e.g., billNo, customer name, etc.
//            content.beginText();
//            content.setFontAndSize(boldFont.getBaseFont(), 12);
//
//            // Example: Placing billNo at coordinates (x, y)
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillSerial()+" "+bill.getBillNo().toString(), 90, 665, 0); // Adjust x, y coordinates as needed
//            content.endText();
//            
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillDate().toString(), 440, 665, 0); // Adjust x, y coordinates as needed
//            
//         // Customer Name in bold
//            content.beginText();
//            content.setFontAndSize(boldFont.getBaseFont(), 12); // Bold and larger font size for the name
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getCustomer().getCustomerName(), 60, 600, 0); // x=250, y=635 for name
//            content.endText();
//            
//            
//         // Address split into three lines (adjust the y-coordinates as needed)
//            content.beginText();
//            content.setFontAndSize(regularFont.getBaseFont(), 12); // Regular font size for the address
//            String[] addressLines = bill.getCustomer().getAddress().split(","); // Assuming address is comma-separated
//
//            if (addressLines.length > 0) {
//                content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines[0], 60, 580, 0); // First line of address
//            }
//            if (addressLines.length > 1) {
//                content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines[1], 60, 560, 0); // Second line of address
//            }
//            if (addressLines.length > 2) {
//                content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines[2], 60, 540, 0); // Third line of address
//            }
//            content.endText();
//            
//
//            // content.showTextAligned(PdfContentByte.ALIGN_LEFT, pledge.getCustomer().getFullAddress(), 250, 625, 0); 
//            // Add more fields as needed, adjusting x and y coordinates accordingly
//            // content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Amount: ₹" + pledge.getAmount(), 100, 690, 0);
//            
////            ColumnText ct = new ColumnText(content);
////            ct.setSimpleColumn( 50,620 ,260, 530); // Define the area: (left x, bottom y, right x, top y)
////
////            // Set the alignment of the text, add the text, and make sure it wraps within the box
////            ct.addText(new Phrase("Address: " + bill.getCustomer().getAddress()));
////            ct.setAlignment(Element.ALIGN_LEFT);
////            ct.go();
//            
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT,  bill.getBillDetails().get(0).getProductDescription(), 50, 475, 0); // Adjust x, y coordinates as needed
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getBillDetails().get(0).getProductQuantity()), 370, 475, 0); // Adjust x, y coordinates as needed
//           
//
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getGrams()), 475, 490, 0); // Adjust x, y coordinates as needed
//            
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getOldbillserialno()), 475, 435, 0); 	
//
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rs. "+ String.valueOf(bill.getPresentValue()) , 470, 255, 0); // Adjust x, y coordinates as needed
//            
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getComments()) , 160, 160, 0); // Adjust x, y coordinates as needed
//            
//            //Set the font and size for the text
//            content.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED), 18);
//
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rs. "+ String.valueOf(bill.getAmount()) , 310, 205, 0); // Adjust x, y coordinates as needed
//            content.endText();
//            
//            stamper.close();
//        }
//        finally {
//            if (stamper != null) {
//                stamper.close();
//            }
//            if (reader != null) {
//                reader.close();
//            }
//            if (fos != null) {
//                fos.close();
//            }
//        }
//   }
//}
////            // Log the successful creation of the PDF
////            System.out.println("PDF generated and saved at: " + outputFileName);
////         
////
////         // Check if ByteArrayOutputStream is empty
////            //System.out.println(fos);
////            File file = new File(outputFilePath);
////            byte[] fileBytes = java.nio.file.Files.readAllBytes(file.toPath());
////            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);
////
////            //fos.write(baos.toByteArray()); // Write the data to the file
////            //fos.flush();
////
////         if (fileBytes.length >= 0) {
////             System.out.println("The ByteArrayOutputStream has data.");
////         } else {
////             System.out.println("The ByteArrayOutputStream is empty.");
////         }
////        
////
////            return byteArrayInputStream;
////
////        } catch (DocumentException | IOException e) {
////            System.err.println("Error generating PDF: " + e.getMessage());
////            throw e;
////        } finally {
////            if (stamper != null) {
////                stamper.close();
////            }
////            if (reader != null) {
////                reader.close();
////            }
////            if (fos != null) {
////                fos.close();
////            }
////        }
////    }
////  
////}
//package com.jewelbankers.services;
//
//
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.jewelbankers.Utility.SettingsUtillity;
//import com.jewelbankers.entity.Bill;
//
//@Service
//public class PdfService {
//	
//	@Autowired
//	SettingsUtillity settingsUtillity;
//
//    private static final String TEMPLATE_PATH = "template/customer.pdf";
//    private static final String OUTPUT_PATH = "bills";
//
//   public ByteArrayInputStream generateAndSaveBillPdf(Bill bill, Map<String, String> settingsMap) throws IOException, DocumentException {
//       // Ensure the output directory exists
//        File dir = new File(OUTPUT_PATH);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        if (!dir.exists()) {
//        	dir.mkdirs();
//        }
//
//        // Set output file path
//        String outputFileName = "bill_" + bill.getBillSerial() + "_" + bill.getBillNo() + ".pdf";
//        String outputFilePath = OUTPUT_PATH + File.separator + outputFileName;
//        
//        System.out.println(outputFilePath);
//        
//        PdfReader reader = null;
//        PdfStamper stamper = null;
//        FileOutputStream fos = null;
//
//        try {
//        	
//        	String shopName = settingsMap.get("SHOP_NAME");
//        	
//        	 String shopLine1 = settingsMap.get("SHOP_NO") + " " + settingsMap.get("SHOP_STREET");
//             String shopLine2 = settingsMap.get("SHOP_AREA") + " " + settingsMap.get("SHOP_CITY");
//             String shopLine3 = settingsMap.get("SHOP_STATE") + " - " + settingsMap.get("SHOP_PINCODE");
//            // Read the template PDF
//            reader = new PdfReader(TEMPLATE_PATH);
//
//            // Create a new PDF with the content filled
//            fos = new FileOutputStream(outputFilePath);
//            stamper = new PdfStamper(reader, fos);
//
//            // Get the PDF content
//            PdfContentByte content = stamper.getOverContent(1);
//
//            // Set the font and size
//            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
//            Font boldFont = new Font(baseFont, 14, Font.BOLD);
//            Font regularFont = new Font(baseFont, 12, Font.NORMAL);
//
//            // Overlay text at specific coordinates
//            content.beginText();
//            
//            content.setFontAndSize(boldFont.getBaseFont(), 14);
//            
//            content.showTextAligned(Element.ALIGN_CENTER, shopName, 300, 740, 0); // Adjust x, y as needed
//            
//         // Shop Address (Regular Font, split into three lines)
//            content.setFontAndSize(regularFont.getBaseFont(), 12);
//            content.showTextAligned(Element.ALIGN_CENTER, shopLine1, 300, 725, 0); // First line
//            
//            content.showTextAligned(Element.ALIGN_CENTER, shopLine2, 300, 710, 0); // Second line
//            
//            content.showTextAligned(Element.ALIGN_CENTER, shopLine3, 300, 695, 0); // Third line
//            content.endText();
//
//            
//         // Add other bill details as necessary, e.g., billNo, customer name, etc.
//            content.beginText();
//            content.setFontAndSize(boldFont.getBaseFont(), 12);
//
//            // Example: Placing billNo at coordinates (x, y)
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillSerial()+" "+bill.getBillNo().toString(), 90, 665, 0); // Adjust x, y coordinates as needed
//            content.endText();
//            
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillDate().toString(), 440, 665, 0); // Adjust x, y coordinates as needed
//            
//         // Customer Name in bold
//            content.beginText();
//            content.setFontAndSize(boldFont.getBaseFont(), 12); // Bold and larger font size for the name
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getCustomer().getCustomerName(), 60, 600, 0); // x=250, y=635 for name
//            content.endText();
//            
//            
//         // Address split into three lines (adjust the y-coordinates as needed)
//            content.beginText();
//            content.setFontAndSize(regularFont.getBaseFont(), 12); // Regular font size for the address
//            String[] addressLines = bill.getCustomer().getAddress().split(","); // Assuming address is comma-separated
//
//            if (addressLines.length > 0) {
//                content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines[0], 60, 580, 0); // First line of address
//            }
//            if (addressLines.length > 1) {
//                content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines[1], 60, 560, 0); // Second line of address
//            }
//            if (addressLines.length > 2) {
//                content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines[2], 60, 540, 0); // Third line of address
//            }
//            content.endText();
//            
//
//            // content.showTextAligned(PdfContentByte.ALIGN_LEFT, pledge.getCustomer().getFullAddress(), 250, 625, 0); 
//            // Add more fields as needed, adjusting x and y coordinates accordingly
//            // content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Amount: ₹" + pledge.getAmount(), 100, 690, 0);
//            
////            ColumnText ct = new ColumnText(content);
////            ct.setSimpleColumn( 50,620 ,260, 530); // Define the area: (left x, bottom y, right x, top y)
////
////            // Set the alignment of the text, add the text, and make sure it wraps within the box
////            ct.addText(new Phrase("Address: " + bill.getCustomer().getAddress()));
////            ct.setAlignment(Element.ALIGN_LEFT);
////            ct.go();
//            
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT,  bill.getBillDetails().get(0).getProductDescription(), 50, 475, 0); // Adjust x, y coordinates as needed
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getBillDetails().get(0).getProductQuantity()), 370, 475, 0); // Adjust x, y coordinates as needed
//           
//
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getGrams()), 475, 490, 0); // Adjust x, y coordinates as needed
//            
//          //  content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getOldbillserialno()), 475, 435, 0);
//            
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getGrams()), 475, 330, 0); // Adjust x, y coordinates as needed
//
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rs. "+ String.valueOf(bill.getPresentValue()) , 470, 255, 0); // Adjust x, y coordinates as needed
//            
//          //  content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getComments()) , 160, 160, 0); // Adjust x, y coordinates as needed
//            
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rs. "+ String.valueOf(bill.getAmountInWords()) , 50, 205, 0); // Adjust x, y coordinates as needed
//
//            
//            //Set the font and size for the text
//            content.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED), 16);
//
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rs. "+ String.valueOf(bill.getAmount()) , 310, 205, 0); // Adjust x, y coordinates as needed
//            
//            content.endText();
//            stamper.close();
//            
//            String rules = settingsMap.get("PLEDGE_RULES");
//            
//            content.setFontAndSize(boldFont.getBaseFont(), 14);
//            
//            content.showTextAligned(Element.ALIGN_LEFT, rules, 50, 145, 0); // Adjust x, y as needed
//            
//            // Log the successful creation of the PDF
//            System.out.println("PDF generated and saved at: " + outputFileName);
//         
//
//         // Check if ByteArrayOutputStream is empty
//            //System.out.println(fos);
//            File file = new File(outputFilePath);
//            byte[] fileBytes = java.nio.file.Files.readAllBytes(file.toPath());
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);
//
//            //fos.write(baos.toByteArray()); // Write the data to the file
//            //fos.flush();
//
//         if (fileBytes.length >= 0) {
//             System.out.println("The ByteArrayOutputStream has data.");
//         } else {
//             System.out.println("The ByteArrayOutputStream is empty.");
//         }
//        
//
//            return byteArrayInputStream;
//
//        } catch (DocumentException | IOException e) {
//            System.err.println("Error generating PDF: " + e.getMessage());
//            throw e;
//        } finally {
//            if (stamper != null) {
//                stamper.close();
//            }
//            if (reader != null) {
//                reader.close();
//            }
//            if (fos != null) {
//                fos.close();
//            }
//        }
//    }
//  
//}


package com.jewelbankers.services;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class CustomerPdfService {

    @Autowired
    SettingsUtillity settingsUtillity;
    
    @Autowired
    SettingsService settingsService;

    private static final String TEMPLATE_PATH = "template/customercopy.pdf";
    private static final String OUTPUT_PATH = "bills";

    public ByteArrayInputStream generateCustomerBillPdf(Bill bill, Map<String, String> settingsMap) throws IOException, DocumentException {
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
            content.showTextAligned(Element.ALIGN_CENTER, shopName, 300, 745, 0);  // Center alignment with coordinates (300, 745)
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
            // Set font to bold, size to 14, and make text black
            content.setFontAndSize(boldFont.getBaseFont(), 13);  // Use a bold font
            content.setColorFill(BaseColor.BLACK);  // Set text color to black

            // Simulate more boldness by setting a thicker stroke width
            content.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
            content.setLineWidth(0.5f);  // Increase line width for more bold effect

            content.showTextAligned(PdfContentByte.ALIGN_LEFT, 
                bill.getBillSerial() + " " + (bill.getBillNo() != null ? bill.getBillNo().toString() : "N/A"), 
                85, 665, 0);
            content.endText();


         // // Define the desired output format
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Desired output format

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
         content.setFontAndSize(regularFont.getBaseFont(), 13);
         content.showTextAligned(PdfContentByte.ALIGN_LEFT, billDateFormatted, 455, 665, 0);
         content.endText();

         //	 (Ensure it's not null)
            String customerName = bill.getCustomer() != null && bill.getCustomer().getCustomerName() != null 
                ? bill.getCustomer().getCustomerName() : "Customer Name";
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, customerName, 60, 610, 0);
            content.endText();

         // Customer Address (Split into lines and ensure it's not null)
            String[] addressLines = bill.getCustomer() != null && bill.getCustomer().getAddress() != null 
                ? bill.getCustomer().getAddress().split(",") : new String[]{"Address Line 1", "Address Line 2", "Address Line 3"};

            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines.length > 0 ? addressLines[0] : "", 60, 590, 0); // First line of address
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines.length > 1 ? addressLines[1] : "", 57, 570, 0); // Second line of address
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, addressLines.length > 2 ? addressLines[2] : "", 57, 550, 0); // Third line of address
            content.endText();

         // Bill Details (Ensure non-null and non-empty lists)
            if (bill.getBillDetails() != null && !bill.getBillDetails().isEmpty()) {
                content.beginText();
                content.setFontAndSize(regularFont.getBaseFont(), 14);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillDetails().get(0).getProductDescription() != null 
                    ? bill.getBillDetails().get(0).getProductDescription() : "N/A", 30, 470, 0);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getBillDetails().get(0).getProductQuantity()), 390, 470, 0);
                content.endText();
            }

         // Grams (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getGrams() != null ? bill.getGrams() : "N/A"), 495, 479, 0);
            content.endText();
            
         // NetWt->Grams (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getGrams() != null ? bill.getGrams() : "N/A"), 495, 387, 0);
            content.endText();	
            
         // Amount Value (Ensure non-null)
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 18);
            content.setLineWidth(0.75f); // Increase the outline width
            content.setColorStroke(new BaseColor(0, 0, 0)); // Outline color (black)
            content.setColorFill(new BaseColor(0, 0, 0)); // Fill color (black)
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rs. " + String.valueOf(bill.getAmount() != null ? bill.getAmount() : "N/A"), 280, 352, 0);
            content.endText();
            
            // AmountInWords Value (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getAmountInWords() != null ? bill.getAmountInWords() : "N/A"), 30, 330, 0);
            content.endText();

         // Present Value (Ensure non-null)
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 14);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getPresentValue() != null ? bill.getPresentValue() : "N/A"), 495, 335, 0);
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
            float yPosition = 300; // Starting Y position for A5
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
            content.setFontAndSize(boldFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillSerial() + " " + 
                (bill.getBillNo() != null ? bill.getBillNo().toString() : "N/A"), 147, 88, 0);
            content.endText();
            
         // Customer Name (Ensure it's not null)
         // String customerName = bill.getCustomer() != null && bill.getCustomer().getCustomerName() != null 
                //? bill.getCustomer().getCustomerName() : "Customer Name";
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, customerName, 147, 70, 0);
            content.endText();
            
         // Define the desired output format
            DateTimeFormatter outputFormatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Desired output format

            // Assuming bill.getBillDate() returns a LocalDate
            LocalDate billDate2 = bill.getBillDate(); // This should return the date as a LocalDate
            String billDateFormatted1;

            // Format the LocalDate object into the desired format
            if (billDate != null) {
                billDateFormatted1 = billDate2.format(outputFormatter1);
            } else {
                billDateFormatted1 = "N/A"; // Default value if the bill date is null
            }

            // Displaying the bill date
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, billDateFormatted1, 140, 55, 0);
            content.endText();
            
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getGrams() != null ? bill.getGrams() : "N/A"), 245, 55, 0);
            content.endText();
            
            content.beginText();
            content.setFontAndSize(boldFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rs. " + String.valueOf(bill.getAmount() != null ? bill.getAmount() : "N/A"), 155, 39, 0);
            content.endText();
            
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 12);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, bill.getBillDetails().get(0).getProductDescription() != null 
                ? bill.getBillDetails().get(0).getProductDescription() : "N/A", 155, 23, 0);
            content.endText();
            
            content.beginText();
            content.setFontAndSize(regularFont.getBaseFont(), 13);
            content.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(bill.getMonthlyIncome() != null ? bill.getMonthlyIncome() : "N/A"), 175, 220, 0);
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
            content.showTextAligned(Element.ALIGN_LEFT, dueDateFormatted, 425, 300, 0); // Adjust the coordinates as needed
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
