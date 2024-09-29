package com.jewelbankers.Utility;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.Customer;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

//@Service
public class AuctionPdfGenerator {

    public byte[] generateAuctionPdf(List<Bill> bills, String auctionDetails, String sirMadamText) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        // Add header
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        document.add(new Paragraph("AMBICA", headerFont));
        document.add(new Paragraph("PAWN BROKER"));
        document.add(new Paragraph(" "));  // Adding space between header and table

        // Add auction details
        if (sirMadamText != null) {
            document.add(new Paragraph(sirMadamText));
            document.add(new Paragraph(" "));
        }

        // Add table with bill details
        PdfPTable table = new PdfPTable(6); // Updated to match the image columns
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 2, 4, 1, 2, 3}); // Setting column widths

        addTableHeader(table);
        addRows(table, bills);

        document.add(table);

        // Footer
        document.add(new Paragraph("From", headerFont));
        document.add(new Paragraph("To"));
        // Assuming the first bill's customer details should be shown in the footer.
        if (!bills.isEmpty()) {
            Customer customer = bills.get(0).getCustomer();
            document.add(new Paragraph(customer.getCustomerName()));
            document.add(new Paragraph(customer.getAddress()));
        }

        if (auctionDetails != null) {
            document.add(new Paragraph(" "));
            document.add(new Paragraph(auctionDetails, headerFont));
        }

        document.close();

        return out.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("BillNo", "CustomerName", "Address", "Weight(grams)", "Amount", "BillDate", "Amount in Words")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<Bill> bills) {
        for (Bill bill : bills) {
            table.addCell(String.valueOf(bill.getBillNo()));
            table.addCell(bill.getCustomer().getCustomerName());
            table.addCell(bill.getCustomer().getAddress());
            table.addCell(String.valueOf(bill.getGrams()));
            table.addCell(String.valueOf(bill.getAmount()));
            table.addCell(bill.getBillDate().toString());
            table.addCell(bill.getAmountInWords());
        }
    }
}
