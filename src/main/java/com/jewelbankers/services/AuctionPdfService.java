package com.jewelbankers.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class AuctionPdfService {

    public ByteArrayInputStream generateAuctionPdf(Map<String, String> auctionDetails) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add the header
            addHeader(document, auctionDetails);

            // Add the auction details
            addAuctionDetails(document, auctionDetails);

            // Add the footer (from address and to address)
            addFooter(document, auctionDetails);

            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addHeader(Document document, Map<String, String> auctionDetails) throws DocumentException {
        Paragraph header = new Paragraph("AUCTION NOTICE",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.BOLD, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(Chunk.NEWLINE);

        Paragraph shopDetails = new Paragraph("AMBICA PAWN BROKER\nNO: 1524, 10TH WEST CROSS STREET,\nM.K.B NAGAR, CHENNAI - 600039",
                FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
        shopDetails.setAlignment(Element.ALIGN_CENTER);
        document.add(shopDetails);
        document.add(Chunk.NEWLINE);
    }

    private void addAuctionDetails(Document document, Map<String, String> auctionDetails) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 4, 4, 2, 4});

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

        PdfPCell hcell;
        hcell = new PdfPCell(new Phrase("Bill Serial", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Bill Date", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Amount", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Product Description", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Weight (gms)", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        // Add auction details row
        table.addCell(auctionDetails.get("billSerial"));
        table.addCell(auctionDetails.get("billDate"));
        table.addCell(auctionDetails.get("amount"));
        table.addCell(auctionDetails.get("productDescription"));
        table.addCell(auctionDetails.get("weight"));

        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void addFooter(Document document, Map<String, String> auctionDetails) throws DocumentException {
        Paragraph fromAddress = new Paragraph("From:\n" +
                "AMBICA PAWN BROKER\n" +
                "NO: 1524, 10TH WEST CROSS STREET,\n" +
                "M.K.B NAGAR, CHENNAI - 600039",
                FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
        fromAddress.setAlignment(Element.ALIGN_LEFT);
        document.add(fromAddress);

        Paragraph toAddress = new Paragraph("To:\n" +
                auctionDetails.get("customerName") + "\n" +
                auctionDetails.get("customerAddress"),
                FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
        toAddress.setAlignment(Element.ALIGN_RIGHT);
        document.add(toAddress);
    }
}
