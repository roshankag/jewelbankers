//package com.jewelbankers.services;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.jewelbankers.Utility.SettingsUtillity;
//import com.jewelbankers.entity.Bill;
//import com.jewelbankers.entity.Jewellery;
//
//@Service
//public class InvoicePdfService {
//
//    @Autowired
//    SettingsUtillity settingsUtillity;
//
//    private static final String TEMPLATE_PATH = "template/invoice_template.pdf";  // Path to the invoice template PDF
//    private static final String OUTPUT_PATH = "invoices";  // Directory where invoices will be saved
//
//    public ByteArrayInputStream generateInvoicePdf(Bill bill,Jewellery invoice, Map<String, String> settingsMap) throws IOException, DocumentException {
//        File dir = new File(OUTPUT_PATH);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        if (!dir.exists()) {
//            dir.mkdirs();  // Create the output directory if it doesn't exist
//        }
//
//        // Set the output file path
//        String outputFileName = "invoice_" + invoice.getInvoiceNo() + ".pdf";
//        String outputFilePath = OUTPUT_PATH + File.separator + outputFileName;
//
//        PdfReader reader = null;
//        PdfStamper stamper = null;
//        FileOutputStream fos = null;
//
//        try {
//            // Fetch Shop Information from settings
//            String shopName = settingsMap.getOrDefault("SHOP_NAME", "Shop Name");
//            String shopAddressLine1 = settingsMap.getOrDefault("SHOP_NO", "") + " " + settingsMap.getOrDefault("SHOP_STREET", "");
//            String shopAddressLine2 = settingsMap.getOrDefault("SHOP_AREA", "") + " " + settingsMap.getOrDefault("SHOP_CITY", "");
//            String shopContact = settingsMap.getOrDefault("SHOP_PHONE", "") + " | " + settingsMap.getOrDefault("SHOP_EMAIL", "");
//
//            // Read the template PDF
//            reader = new PdfReader(TEMPLATE_PATH);
//            fos = new FileOutputStream(outputFilePath);
//            stamper = new PdfStamper(reader, fos);
//
//            Rectangle a4Size = PageSize.A4;
//            stamper.getWriter().setPageSize(a4Size);
//
//            // Get the PDF content
//            PdfContentByte content = stamper.getOverContent(1);
//
//            // Set font styles
//            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
//            Font boldFont = new Font(baseFont, 14, Font.BOLD);
//            Font regularFont = new Font(baseFont, 12, Font.NORMAL);
//
//            // Shop Name and Address
//            content.beginText();
//            content.setFontAndSize(boldFont.getBaseFont(), 16);
//            content.showTextAligned(Element.ALIGN_CENTER, shopName, 300, 780, 0);
//            content.setFontAndSize(regularFont.getBaseFont(), 12);
//            content.showTextAligned(Element.ALIGN_CENTER, shopAddressLine1, 300, 760, 0);
//            content.showTextAligned(Element.ALIGN_CENTER, shopAddressLine2, 300, 745, 0);
//            content.showTextAligned(Element.ALIGN_CENTER, shopContact, 300, 730, 0);
//            content.endText();
//
//            // Invoice No and Date
//            content.beginText();
//            content.setFontAndSize(boldFont.getBaseFont(), 12);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "INVOICE NO: " + invoice.getInvoiceNo(), 90, 680, 0);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "DATE: " + new SimpleDateFormat("dd/MM/yyyy").format(invoice.getInvoiceDate()), 400, 680, 0);
//            content.endText();
//            
//            // Customer Information
//            content.beginText();
//            String customerName = bill.getCustomer() != null && bill.getCustomer().getCustomerName() != null
//                ? bill.getCustomer().getCustomerName() : "Customer Name";
//           
//            Long customerPhone = bill.getCustomer() != null && bill.getCustomer().getPhoneno() != null
//                    ? bill.getCustomer().getPhoneno() : null;
//            
//            String customerEmail = bill.getCustomer() != null && bill.getCustomer().getMailid() != null
//                ? bill.getCustomer().getMailid() : "Email Address";
//            content.setFontAndSize(boldFont.getBaseFont(), 12);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Customer: " + customerName, 35, 640, 0);
//            content.setFontAndSize(regularFont.getBaseFont(), 12);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Phone: " + customerPhone, 35, 620, 0);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Email: " + customerEmail, 35, 600, 0);
//            content.endText();
//
//            // Item Information
//            content.beginText();
//            content.setFontAndSize(boldFont.getBaseFont(), 12);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Item Description: " + invoice.getItemDescription(), 35, 640, 0);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Weight: " + invoice.getWeight(), 35, 620, 0);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Quantity: " + invoice.getItemQuantity(), 35, 600, 0);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Rate: ₹" + String.format("%.2f", invoice.getItemRate()), 35, 580, 0);
//            content.endText();
//
//            // Prize and Total Prize
//            content.beginText();
//            content.setFontAndSize(regularFont.getBaseFont(), 12);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Prize: ₹" + invoice.getPrize(), 35, 540, 0);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Total Prize: ₹" + invoice.getTotalPrize(), 35, 520, 0);
//            content.endText();
//
//         // Fetch the CGST and SGST values from settings
//            BigDecimal cgstRate = settingsUtillity.getCgst(settingsMap);
//            BigDecimal sgstRate = settingsUtillity.getSgst(settingsMap);
//
//            // Calculate the CGST and SGST using the fetched rates
//            BigDecimal cgst = invoice.getTotalPrize().multiply(cgstRate);
//            BigDecimal sgst = invoice.getTotalPrize().multiply(sgstRate);
//            BigDecimal grandTotal = invoice.getTotalPrize().add(cgst).add(sgst);
//
//            // PDF Content Creation
//            content.beginText();
//            content.setFontAndSize(boldFont.getBaseFont(), 12);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "CGST (" + cgstRate.multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP) + "%): ₹" + String.format("%.2f", cgst), 400, 200, 0);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "SGST (" + sgstRate.multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP) + "%): ₹" + String.format("%.2f", sgst), 400, 180, 0);
//            content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Grand Total: ₹" + String.format("%.2f", grandTotal), 400, 160, 0);
//            content.endText();
//
//            // Footer
//            content.beginText();
//            content.setFontAndSize(regularFont.getBaseFont(), 12);
//            content.showTextAligned(Element.ALIGN_CENTER, "For YOUR COMPANY NAME", 300, 100, 0);
//            content.showTextAligned(Element.ALIGN_CENTER, "Authorized Signature", 300, 80, 0);
//            content.endText();
//
//            // Close the stamper and return the generated PDF
//            stamper.close();
//            File file = new File(outputFilePath);
//            byte[] fileBytes = java.nio.file.Files.readAllBytes(file.toPath());
//            return new ByteArrayInputStream(fileBytes);
//
//        } catch (IOException | DocumentException e) {
//            e.printStackTrace();
//            throw e;
//        } finally {
//            if (fos != null) {
//                fos.close();
//            }
//            if (reader != null) {
//                reader.close();
//            }
//        }
//    }
//}
