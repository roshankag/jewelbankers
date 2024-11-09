package com.jewelbankers.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;
import com.jewelbankers.entity.Customer;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Stream;

@Service
public class BillPdfService {

    public ByteArrayInputStream generateBillPdf(List<Bill> bills) {
        // Sort bills by date in descending order
        bills.sort(Comparator.comparing(Bill::getBillDate).reversed());

        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // A4 size with custom margins
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add heading
            addHeading(document);

            // Add bill details table
            addBillDetailsTable(bills, document);

            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addHeading(Document document) throws DocumentException {
        Paragraph header = new Paragraph("Bill Details",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Font.BOLD, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(Chunk.NEWLINE);
    }

    private void addBillDetailsTable(List<Bill> bills, Document document) throws DocumentException {
        // Adjust column widths for better alignment and readability
        PdfPTable table = new PdfPTable(new float[]{1, 1, 1.5f, 2, 2.5f, 3, 1});
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        // Add table headers with background color
        addTableHeader(table, headFont);

        // Add table data rows
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Bill bill : bills) {
            addTableRow(bill, table, cellFont, formatter);
        }

        document.add(table);
    }

    private void addTableHeader(PdfPTable table, Font headFont) {
        Stream.of("Bill Serial", "Bill No", "Bill Date", "Customer Name", "Address", "Product Description", "Grams")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell(new Phrase(columnTitle, headFont));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    header.setPadding(6);
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY); // Add a light gray background for headers
                    table.addCell(header);
                });
    }

    private void addTableRow(Bill bill, PdfPTable table, Font cellFont, DateTimeFormatter formatter) {
        table.addCell(createTableCell(String.valueOf(bill.getBillSerial()), cellFont));
        table.addCell(createTableCell(String.valueOf(bill.getBillNo()), cellFont));
        
        // Format the bill date to dd-MM-yyyy
        table.addCell(createTableCell(bill.getBillDate().format(formatter), cellFont));
        
        // Customer details
        Customer customer = bill.getCustomer();
        table.addCell(createTableCell(customer != null ? customer.getCustomerName() : "", cellFont));
        table.addCell(createTableCell(customer != null ? customer.getAddress() : "", cellFont));
        
        // Product description from BillDetails
        StringBuilder productDesc = new StringBuilder();
        for (BillDetail billDetail : bill.getBillDetails()) {
            productDesc.append(billDetail.getProductDescription()).append("; ");
        }
        table.addCell(createTableCell(productDesc.toString(), cellFont));

        // Align grams to the right for consistency with numerical data
        PdfPCell gramsCell = createTableCell(String.valueOf(bill.getGrams()), cellFont);
        gramsCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(gramsCell);
    }

    private PdfPCell createTableCell(String content, Font font) {
        if (content == null) content = ""; // Handle null
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(4);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // Center-align vertically
        cell.setBorderWidth(0.5f); // Add a subtle border
        return cell;
    }
}
