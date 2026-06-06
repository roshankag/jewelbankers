package com.jewelbankers.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;
import com.jewelbankers.entity.Customer;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Stream;

@Service
public class BillPdfService {

    public ByteArrayInputStream generateBillPdf(List<Bill> bills, String shopName, Map<String, String> shopAddress) {
        // Sort bills by date in descending order
        bills.sort(Comparator.comparing(Bill::getBillDate).reversed());

        Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20); // Compact margins for more content
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            addHeading(document, shopName, shopAddress);
            addBillDetailsTable(bills, document);

            out.flush();

            //document.close();  // Close the document and finish writing

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (document.isOpen()) {
                document.close();
            }
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addHeading(Document document, String shopName, Map<String, String> shopAddress) throws DocumentException {
        Paragraph header = new Paragraph("Bill Details",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(Chunk.NEWLINE);

        StringBuilder shopDetails = new StringBuilder();
        if (shopName != null) {
            shopDetails.append(shopName).append("\n");
        }
        if (shopAddress != null) {
            String line1 = shopAddress.getOrDefault("SHOP_NO", "") + ", " + shopAddress.getOrDefault("SHOP_STREET", "");
            String line2 = shopAddress.getOrDefault("SHOP_AREA", "") + ", " + shopAddress.getOrDefault("SHOP_CITY", "");
            String line3 = shopAddress.getOrDefault("SHOP_STATE", "") + " - " + shopAddress.getOrDefault("SHOP_PINCODE", "");

            shopDetails.append(line1).append("\n").append(line2).append("\n").append(line3);
        }

        Paragraph shopDetailsParagraph = new Paragraph(shopDetails.toString(),
                FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK));
        shopDetailsParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(shopDetailsParagraph);
    }

    private void addBillDetailsTable(List<Bill> bills, Document document) throws DocumentException {
        // Split Customer Details into Name and Address to match the Excel format and fit on one line
        PdfPTable table = new PdfPTable(new float[]{1.2f, 2.6f, 5.2f, 1.5f, 1.2f, 1.3f, 1.1f, 2.8f, 1.3f, 1.5f, 1.1f});
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8.0f);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 7.5f);
        Font customerNameFont = FontFactory.getFont(FontFactory.HELVETICA, 7.5f);
        Font customerAddressFont = FontFactory.getFont(FontFactory.HELVETICA, 7.0f);

        addTableHeader(table, headFont);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Bill bill : bills) {
            addTableRow(bill, table, cellFont, customerNameFont, customerAddressFont, formatter);
        }

        document.add(table);
    }

    private void addTableHeader(PdfPTable table, Font headFont) {
        Stream.of("Pledge No", "Customer Name", "Customer Address", "Date", "Amount", "Redem Total", 
                  "Weight", "Product Desc", "Redem No", "Redem Date", "Status")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell(new Phrase(columnTitle, headFont));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    header.setPadding(5);
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(header);
                });
    }

    private void addTableRow(Bill bill, PdfPTable table, Font cellFont, Font customerNameFont, Font customerAddressFont, DateTimeFormatter formatter) {
        // Pledge No
        String pledgeNo = bill.getBillSerial() != null && bill.getBillNo() != null ? bill.getBillSerial() + "" + bill.getBillNo() : ""; // Handle null
        table.addCell(createTableCell(pledgeNo, cellFont));

        // Customer Name & Customer Address
        Customer customer = bill.getCustomer();
        String customerName = "";
        String customerAddress = "";
        if (customer != null) {
            customerName = customer.getCustomerName() != null ? customer.getCustomerName() : "";
            customerAddress = customer.getAddress() != null ? customer.getAddress() : "";
            Long mobile = customer.getMobileno();
            if (mobile == null || mobile == 0) {
                mobile = customer.getPhoneno();
            }
            if (mobile != null && mobile != 0) {
                if (!customerAddress.isEmpty()) {
                    customerAddress += ", Mob: " + mobile;
                } else {
                    customerAddress = "Mob: " + mobile;
                }
            }
        }
        table.addCell(createTableCell(customerName, customerNameFont, Element.ALIGN_CENTER));
        table.addCell(createTableCell(customerAddress, customerAddressFont, Element.ALIGN_CENTER));

        // Date
        table.addCell(createTableCell(bill.getBillDate() != null ? bill.getBillDate().format(formatter) : "", cellFont)); // Handle null

        // Amount
        table.addCell(createTableCell(bill.getAmount() != null ? String.valueOf(bill.getAmount()) : "", cellFont)); // Handle null
        
        // Redemption Total
        String redemTotal = "";
        if (bill.getRedemptionStatus() != null && bill.getRedemptionStatus() != 'O' && bill.getRedemptionTotal() != null) {
            redemTotal = bill.getRedemptionTotal() != 0 ? String.valueOf(bill.getRedemptionTotal()) : "";
        }
        table.addCell(createTableCell(redemTotal, cellFont)); // Display blank if open or 0

        // Weight /GM
        table.addCell(createTableCell(bill.getGrams() != null ? String.valueOf(bill.getGrams()) : "", cellFont)); // Handle null

        // Product Description
        StringBuilder ornamentDetails = new StringBuilder();
        for (BillDetail billDetail : bill.getBillDetails()) {
            if (billDetail.getProductDescription() != null) {
                ornamentDetails.append(billDetail.getProductDescription()).append("; ");
            }
        }
        table.addCell(createTableCell(ornamentDetails.toString().trim(), cellFont)); // Display full product description

        // Redemption No
        String redemNo = bill.getBillRedemSerial() != null && bill.getBillRedemNo() != null ? bill.getBillRedemSerial() + "" + bill.getBillRedemNo() : ""; // Handle null
        table.addCell(createTableCell(redemNo, cellFont));

        // Redemption Date
        String redemptionDate = (bill.getRedemptionDate() != null) ? bill.getRedemptionDate().format(formatter) : ""; // Handle null
        table.addCell(createTableCell(redemptionDate, cellFont));

     // Status
        String status = "";
        if (bill.getRedemptionStatus() != null) {
            char redemptionStatus = bill.getRedemptionStatus();  // Assuming it's a char
            if (redemptionStatus == 'R') {
                status = "Paid"; // Display "Paid" for redemption status "R"
            } else if (redemptionStatus != 'O') {
                status = ""; // For all other statuses, display blank
            }
        }
        table.addCell(createTableCell(status, cellFont));
    } 

    private PdfPCell createTableCell(String content, Font font) {
        return createTableCell(content, font, Element.ALIGN_CENTER);
    }

    private PdfPCell createTableCell(String content, Font font, int alignment) {
        // Adjusted to handle both `null` and "null" string values by displaying them as blank
        if (content == null || "null".equals(content)) content = ""; // Handle null or "null" strings
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(3);
        if (alignment == Element.ALIGN_LEFT) {
            cell.setPaddingLeft(5);
        }
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(16); // Use minimum height instead of fixed height so it can wrap and grow
        return cell;
    }
}