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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuctionPdfService {

    public Map<String, List<Bill>> groupBillsByCustomerName(List<Bill> bills) {
        return bills.stream()
                .collect(Collectors.groupingBy(bill -> bill.getCustomer().getCustomerName()));
    }

    public ByteArrayInputStream generateAuctionPdf(List<Bill> bills, Map<String, String> auctionDetails, String fromAddressText, String auctionDescription, String shopName) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // A4 size with custom margins
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();            

            for (Map.Entry<String, List<Bill>> entry : groupBillsByCustomerName(bills).entrySet()) {
                // Add the header
                addHeader(document, auctionDescription, shopName);

                // Add the auction details
                addAuctionDetails(entry.getValue(), document);

                // Add the footer (from address and to address)
                addFooter(document, entry.getValue().get(0).getCustomer(), fromAddressText);

                document.newPage();
            }

            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addHeader(Document document, String auctionDescription, String shopName) throws DocumentException {
        // Header Title
        Paragraph header = new Paragraph("AUCTION NOTICE",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.BOLD, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(Chunk.NEWLINE);

        // Shop Name
        String shopNameText = shopName != null ? shopName : ""; // Handle null
        Paragraph shopDetailsParagraph = new Paragraph(shopNameText,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.NORMAL, BaseColor.BLACK));
        shopDetailsParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(shopDetailsParagraph);
        document.add(Chunk.NEWLINE);

        // Auction Description
        String auctionDescriptionText = auctionDescription != null ? auctionDescription : ""; // Handle null
        Paragraph notice = new Paragraph(auctionDescriptionText,
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Bill bill : bills) {
            addTableRow(bill, table, cellFont, formatter);
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

    private void addTableRow(Bill bill, PdfPTable table, Font cellFont, DateTimeFormatter formatter) {
        table.addCell(createTableCell(bill.getBillSerial() + String.valueOf(bill.getBillNo()), cellFont));
        
        // Format the bill date to dd-MM-yyyy
        table.addCell(createTableCell(bill.getBillDate().format(formatter), cellFont));
        
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
        if (content == null) content = ""; // Handle null
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private void addFooter(Document document, Customer customer, String fromAddressText) throws DocumentException {
        // Create a table with two columns
        PdfPTable addressTable = new PdfPTable(2); 
        addressTable.setWidthPercentage(100); // Set table width to 100% of the page

        // Define column widths for both "From" and "To" sections
        float[] columnWidths = {50f, 50f}; 
        addressTable.setWidths(columnWidths);

        // Create "From" address cell
        PdfPCell fromCell = new PdfPCell();
        fromCell.setBorder(PdfPCell.NO_BORDER); // No border for a clean look
        fromCell.addElement(new Paragraph(fromAddressText, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));

        // Create "To" address cell
        PdfPCell toCell = new PdfPCell();
        toCell.setBorder(PdfPCell.NO_BORDER); // No border for a clean look
        toCell.addElement(new Paragraph("To:", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
        toCell.addElement(new Paragraph(customer.getCustomerName(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));

        if (!customer.isFullAddress()) {
            toCell.addElement(new Paragraph(customer.getAddress(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
        } else {
            toCell.addElement(new Paragraph(customer.getAddressArea(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
            toCell.addElement(new Paragraph(customer.getStreetDistrict(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
            toCell.addElement(new Paragraph(customer.getStatePincode(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
        }

        // Add both cells to the table
        addressTable.addCell(fromCell);
        addressTable.addCell(toCell);

        // Add the table to the document
        document.add(addressTable);
    }
}






//package com.jewelbankers.services;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.*;
//import com.jewelbankers.entity.Bill;
//import com.jewelbankers.entity.BillDetail;
//import com.jewelbankers.entity.Customer;
//
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Service
//public class AuctionPdfService {
//
//	public Map<String, List<Bill>> groupBillsByCustomerName(List<Bill> bills) {
//	    return bills.stream()
//	            .collect(Collectors.groupingBy(bill -> bill.getCustomer().getCustomerName()));
//	}
//	
//    public ByteArrayInputStream generateAuctionPdf(List<Bill> bills, Map<String, String> auctionDetails, String fromAddressText, String auctionDescription, String shopName) {
//        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // A4 size with custom margins
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        try {
//            PdfWriter.getInstance(document, out);
//            document.open();            
//            
//            for (Map.Entry<String, List<Bill>> entry : groupBillsByCustomerName(bills).entrySet()) {
//            	// Add the header
//                addHeader(document, auctionDescription, shopName);
//                
//                // Add the auction details
//                addAuctionDetails(entry.getValue(), document);
//                
//                // Add the footer (from address and to address)
//                addFooter(document, entry.getValue().get(0).getCustomer(), fromAddressText);
//
//                document.newPage();
//            }
//
//            document.close();
//
//        } catch (DocumentException ex) {
//            ex.printStackTrace();
//        }
//
//        return new ByteArrayInputStream(out.toByteArray());
//    }
//
//    private void addHeader(Document document, String auctionDescription, String shopName) throws DocumentException {
//        // Header Title
//        Paragraph header = new Paragraph("AUCTION NOTICE",
//                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.BOLD, BaseColor.BLACK));
//        header.setAlignment(Element.ALIGN_CENTER);
//        document.add(header);
//        document.add(Chunk.NEWLINE);
//
//        // Shop Name
//        String shopNameText = shopName != null ? shopName : ""; // Handle null
//        Paragraph shopDetailsParagraph = new Paragraph(shopNameText,
//                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.NORMAL, BaseColor.BLACK));
//        shopDetailsParagraph.setAlignment(Element.ALIGN_CENTER);
//        document.add(shopDetailsParagraph);
//        document.add(Chunk.NEWLINE);
//
//        // Auction Description
//        String auctionDescriptionText = auctionDescription != null ? auctionDescription : ""; // Handle null
//        Paragraph notice = new Paragraph(auctionDescriptionText,
//                FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK));
//        notice.setAlignment(Element.ALIGN_JUSTIFIED);
//        document.add(notice);
//        document.add(Chunk.NEWLINE);
//    }
//
//    private void addAuctionDetails(List<Bill> bills, Document document) throws DocumentException {
//        PdfPTable table = new PdfPTable(new float[]{1, 2, 2, 4, 1, 1});
//        table.setWidthPercentage(100);
//        table.setSpacingBefore(10f);
//        table.setSpacingAfter(10f);
//
//        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
//        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
//
//        // Add table headers
//        addTableHeader(table, headFont);
//
//        // Add table data
//        for (Bill bill : bills) {
//            addTableRow(bill, table, cellFont);
//        }
//
//        document.add(table);
//    }
//
//    private void addTableHeader(PdfPTable table, Font headFont) {
//        Stream.of("Bill No", "Bill Date", "Amount", "Product Description", "Grams", "Amount in Words")
//                .forEach(columnTitle -> {
//                    PdfPCell header = new PdfPCell(new Phrase(columnTitle, headFont));
//                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    header.setPadding(5);
//                    table.addCell(header);
//                });
//    }
//
//    private void addTableRow(Bill bill, PdfPTable table, Font cellFont) {
//        table.addCell(createTableCell(bill.getBillSerial() + String.valueOf(bill.getBillNo()), cellFont));
//        table.addCell(createTableCell(bill.getBillDate().toString(), cellFont));
//        table.addCell(createTableCell(String.valueOf(bill.getAmount()), cellFont));
//
//        StringBuilder productDesc = new StringBuilder();
//        for (BillDetail billDetail : bill.getBillDetails()) {
//            productDesc.append(billDetail.getProductDescription());
//        }
//        table.addCell(createTableCell(productDesc.toString(), cellFont));
//        table.addCell(createTableCell(String.valueOf(bill.getGrams()), cellFont));
//        table.addCell(createTableCell(bill.getAmountInWords(), cellFont));
//    }
//
//    private PdfPCell createTableCell(String content, Font font) {
//    	if (content == null) content = ""; // Handle null
//        PdfPCell cell = new PdfPCell(new Phrase(content, font));
//        cell.setPadding(5);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        return cell;
//    }
//
//    private void addFooter(Document document, Customer customer, String fromAddressText) throws DocumentException {
//    	 //String shopNameText = shopName != null ? shopName : ""; // Handle null
//    	 
//    	// Create a table with two columns
//    	PdfPTable addressTable = new PdfPTable(2); 
//    	addressTable.setWidthPercentage(100); // Set table width to 100% of the page
//
//    	// Define column widths for both "From" and "To" sections
//    	float[] columnWidths = {50f, 50f}; 
//    	addressTable.setWidths(columnWidths);
//
//    	// Create "From" address cell
//    	PdfPCell fromCell = new PdfPCell();
//    	fromCell.setBorder(PdfPCell.NO_BORDER); // No border for a clean look
//    	
//    	fromCell.addElement(new Paragraph(fromAddressText, FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
//
//    	
//    	// Create "To" address cell
//    	PdfPCell toCell = new PdfPCell();
//    	
//    	toCell.setBorder(PdfPCell.NO_BORDER); // No border for a clean look
//    	toCell.addElement(new Paragraph("To:", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
//    	toCell.addElement(new Paragraph(customer.getCustomerName(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
//    	
//    	if(!customer.isFullAddress()) {
//        	toCell.addElement(new Paragraph(customer.getAddress(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
//
//    	}else
//    	{
//    		toCell.addElement(new Paragraph(customer.getAddressArea(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
//        	toCell.addElement(new Paragraph(customer.getStreetDistrict(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
//        	toCell.addElement(new Paragraph(customer.getStatePincode(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
//        	
//    	} 
//    	
//    	// Add both cells to the table
//    	addressTable.addCell(fromCell);
//    	addressTable.addCell(toCell);
//
//    	// Add the table to the document
//    	document.add(addressTable);
//    }
//}    
