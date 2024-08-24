package com.jewelbankers.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.jewelbankers.Utility.SettingsUtillity;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;
import com.jewelbankers.entity.Settings;
import com.jewelbankers.repository.SettingsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class AuctionPdfService {
	
//	@Autowired
//	private SettingsRepository settingsRepository;
//	

    public ByteArrayInputStream generateAuctionPdf(List<Bill> bills , Map<String, String> auctionDetails, String shopDetails, String auctionDescription) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            
           

            // Add the header
            addHeader(document, auctionDetails, shopDetails, auctionDescription);

            // Add the auction details
            addAuctionDetails(bills,document, auctionDetails);

            // Add the footer (from address and to address)
            addFooter(document, auctionDetails,shopDetails);

            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    
    private void addHeader(Document document, Map<String, String> auctionDetails, String shopDetails, String auctionDescription) throws DocumentException {
//    	List<Settings> settings = settingsRepository.findAll();
//    	Map<String,String> settingsMap =  settingsUtility.convertListToMap(settings);
        Paragraph header = new Paragraph("AUCTION NOTICE",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.BOLD, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(Chunk.NEWLINE);
        
        Paragraph shopDetailsParagraph = new Paragraph(shopDetails,
                FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
        shopDetailsParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(shopDetailsParagraph);
        document.add(Chunk.NEWLINE);
        
        document.add(new Paragraph(auctionDescription));
 
       // Paragraph shopDetails = new Paragraph("AMBICA PAWN BROKER\nNO: 1524, 10TH WEST CROSS STREET,\nM.K.B NAGAR, CHENNAI - 600039",
        
    }

    private void addAuctionDetails(List<Bill> bills,Document document, Map<String, String> auctionDetails) throws DocumentException {
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
//        
//        if (bills == null || bills.isEmpty()) {
//            return ResponseEntity.badRequest().body("No bill found for the provided search criteria.");
//        }

        // Assuming we're generating the PDF for the first matched bill
//        Bill bill = bills.get(0);
        
//        Map<String, String> auctionDetails = new HashMap<>();
//        auctionDetails.put("billSerial", bill.getBillSerial() + String.valueOf(bill.getBillNo()));
//        auctionDetails.put("billDate", bill.getBillDate().toString());
//        auctionDetails.put("amount", String.valueOf(bill.getAmount()));
//        auctionDetails.put("weight", String.valueOf(bill.getGrams()));
//        auctionDetails.put("customerName", bill.getCustomer().getCustomerName());
//        auctionDetails.put("customerAddress", bill.getCustomer().getAddress());
        
        for (Bill bill : bills) {
        	// Add auction details row
            table.addCell(bill.getBillSerial() + String.valueOf(bill.getBillNo()));
            table.addCell( bill.getBillDate().toString());
            table.addCell(String.valueOf(bill.getAmount()));
            StringBuffer productDesc = new StringBuffer();
            for (BillDetail billDetail : bill.getBillDetails()) {
            	productDesc.append(billDetail.getProductDescription());
            
            }
            table.addCell(productDesc.toString());
            table.addCell(String.valueOf(bill.getGrams()));
		}
        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void addFooter(Document document, Map<String, String> auctionDetails, String shopDetails) throws DocumentException {
        Paragraph fromAddress = new Paragraph(shopDetails,
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
