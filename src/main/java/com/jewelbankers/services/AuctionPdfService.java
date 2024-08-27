package com.jewelbankers.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class AuctionPdfService {

    public ByteArrayInputStream generateAuctionPdf(List<Bill> bills, Map<String, String> auctionDetails, String shopDetails, String auctionDescription, String shopName) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // A4 size with custom margins
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add the header
            addHeader(document, shopDetails, auctionDescription, shopName);

            // Add the auction details
            addAuctionDetails(bills, document);

            // Add the footer (from address and to address)
            addFooter(document, auctionDetails, shopName);

            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addHeader(Document document, String shopDetails, String auctionDescription, String shopName) throws DocumentException {
        // Header Title
        Paragraph header = new Paragraph("AUCTION NOTICE",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.BOLD, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(Chunk.NEWLINE);

        // Shop Name
        Paragraph shopDetailsParagraph = new Paragraph(shopName,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.NORMAL, BaseColor.BLACK));
        shopDetailsParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(shopDetailsParagraph);
        document.add(Chunk.NEWLINE);

        // Auction Description
        Paragraph notice = new Paragraph(auctionDescription,
                FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
        notice.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(notice);
        document.add(Chunk.NEWLINE);
    }

    private void addAuctionDetails(List<Bill> bills, Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(new float[]{1, 2, 2, 4, 1, 1});
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        // Add table headers
        addTableHeader(table, headFont);

        // Add table data
        for (Bill bill : bills) {
            addTableRow(bill, table, cellFont);
        }

        document.add(table);
    }

    private void addTableHeader(PdfPTable table, Font headFont) {
        Stream.of("Bill No", "Bill Date", "Amount", "Product Description", "Grams", "Amount in Words")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell(new Phrase(columnTitle, headFont));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setPadding(5);
                    table.addCell(header);
                });
    }

    private void addTableRow(Bill bill, PdfPTable table, Font cellFont) {
        table.addCell(createTableCell(bill.getBillSerial() + String.valueOf(bill.getBillNo()), cellFont));
        table.addCell(createTableCell(bill.getBillDate().toString(), cellFont));
        table.addCell(createTableCell(String.valueOf(bill.getAmount()), cellFont));

        StringBuilder productDesc = new StringBuilder();
        for (BillDetail billDetail : bill.getBillDetails()) {
            productDesc.append(billDetail.getProductDescription());
        }
        table.addCell(createTableCell(productDesc.toString(), cellFont));
        table.addCell(createTableCell(String.valueOf(bill.getGrams()), cellFont));
        table.addCell(createTableCell(bill.getAmountInWords(), cellFont));
    }

    private PdfPCell createTableCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private void addFooter(Document document, Map<String, String> auctionDetails, String shopName) throws DocumentException {
        // From Address
        Paragraph fromAddress = new Paragraph("From:\n" + shopName,
                FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
        fromAddress.setAlignment(Element.ALIGN_LEFT);
        document.add(fromAddress);

        // To Address
        Paragraph toAddress = new Paragraph("To:\n" +
                auctionDetails.get("customerName") + "\n" +
                auctionDetails.get("customerAddress"),
                FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
        toAddress.setAlignment(Element.ALIGN_RIGHT);
        document.add(toAddress);
    }
}
