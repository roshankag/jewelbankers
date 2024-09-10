package com.jewelbankers.Utility;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

public class BillPdfGenerator {

    public static ByteArrayInputStream generatePledgeBill(Bill bill, Map<String, String> shopDetails) {

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        Font fontBody = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Shop details
            String shopInfo = shopDetails.get("SHOP_NAME") + " " +
                              shopDetails.get("SHOP_NO") + ", " +
                              shopDetails.get("SHOP_STREET") + "\n" +
                              shopDetails.get("SHOP_AREA") + "\n" +
                              shopDetails.get("SHOP_CITY") + " - " + shopDetails.get("SHOP_PINCODE") + "\n" +
                              shopDetails.get("SHOP_STATE");

            Paragraph shopDetailsParagraph = new Paragraph(shopInfo, fontHeader);
            shopDetailsParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(shopDetailsParagraph);

         // Bill Number and Date
            PdfPTable billTable = new PdfPTable(2);
            billTable.setWidthPercentage(100);
            billTable.setWidths(new int[]{1, 1});

            PdfPCell cell;

            // Bill Number
            cell = new PdfPCell(new Phrase("BILL NO: " + bill.getBillNo(), fontBody));
            cell.setBorderWidth(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);  // Align bill number to the left
            billTable.addCell(cell);

            // Date
            cell = new PdfPCell(new Phrase("DATE: " + bill.getBillDate(), fontBody));
            cell.setBorderWidth(0);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);  // Align date to the right
            billTable.addCell(cell);

            document.add(billTable);


            // Customer Name and Address
            Paragraph customerInfo = new Paragraph("CUSTOMER NAME AND ADDRESS:\n" +
                    bill.getCustomer().getCustomerName() + "\n" +
                    bill.getCustomer().getStreet() + ", " + bill.getCustomer().getArea() + "\n" +
                    bill.getCustomer().getDistrict() + ", " + bill.getCustomer().getPincode(), fontBody);
            customerInfo.setAlignment(Element.ALIGN_LEFT);
            customerInfo.setSpacingBefore(10);
            customerInfo.setSpacingAfter(20);
            document.add(customerInfo);

            // Product Details Table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 2, 1, 1});

            // Header row
            table.addCell(new PdfPCell(new Phrase("Description", fontBody)));
            table.addCell(new PdfPCell(new Phrase("ProductNo", fontBody)));
            table.addCell(new PdfPCell(new Phrase("Weight", fontBody)));
            table.addCell(new PdfPCell(new Phrase("Present Value", fontBody)));

            for (BillDetail detail : bill.getBillDetails()) {
                table.addCell(new PdfPCell(new Phrase(detail.getProductDescription(), fontBody)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(detail.getProductNo()), fontBody)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(bill.getGrams()), fontBody)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(bill.getPresentValue()), fontBody)));
            }

            document.add(table);

         // Define a bold font for amount information
            Font fontBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

            // Amount and Amount in Words
            Paragraph amountInfo = new Paragraph("Amount: " + bill.getAmount() +
                    "\n" + bill.getAmountInWords(), fontBold);
            amountInfo.setAlignment(Element.ALIGN_LEFT);
            amountInfo.setSpacingBefore(10);
            document.add(amountInfo);


            // Signature
            String shopName = shopDetails.get("SHOP_NAME");
            Paragraph signature = new Paragraph("For " + shopName + "\nSignature of the Pawner", fontBody);
            signature.setAlignment(Element.ALIGN_RIGHT);
            signature.setSpacingBefore(10);
            document.add(signature);

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
