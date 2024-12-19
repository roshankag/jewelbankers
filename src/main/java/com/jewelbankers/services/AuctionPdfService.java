package com.jewelbankers.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jewelbankers.Utility.SettingsUtillity;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.BillDetail;
import com.jewelbankers.entity.Customer;

@Service
public class AuctionPdfService {
	
	@Autowired
	private SettingsUtillity settingsUtillity;
	
	public Map<String, List<Bill>> groupBillsByCustomerName(List<Bill> bills) {
	    return bills.stream()
	            .filter(bill -> bill.getCustomer() != null && bill.getCustomer().getCustomerName() != null)
	            .collect(Collectors.groupingBy(bill -> bill.getCustomer().getCustomerName()));
	}
	
	private Font getTamilFont() {
	    try {
	        // Path to the Tamil font file (example: Latha.ttf or Bamini.ttf)
	        String fontPath = "template/latha.ttf"; // Update this with the correct path to your font file

	        // Load the font and create a Font object
	        BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	        return new Font(baseFont, 12); // Set the font size as required
	    } catch (Exception e) {
	        e.printStackTrace();
	        return FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK); // Fallback font if error occurs
	    }
	}


	
    public ByteArrayInputStream generateAuctionPdf(List<Bill> bills, Map<String, String> auctionDetails, String fromAddressText, String shopName,  Map<String, String> shopAddress, Map<String, String> settingsMap) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // A4 size with custom margins
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();     
            
         // Use the Tamil font for displaying Tamil text
            Font tamilFont = getTamilFont();

            for (Map.Entry<String, List<Bill>> entry : groupBillsByCustomerName(bills).entrySet()) {
                // Add the header
                addHeader(document, shopName, entry.getValue().get(0).getCustomer(), shopAddress, settingsMap, tamilFont);

                // Add the auction details
                addAuctionDetails(entry.getValue(), document);

                // Add the footer (from address and to address)
                addFooter(document, entry.getValue().get(0).getCustomer(), fromAddressText);

                document.newPage();
            }

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

    private void addHeader(Document document, String shopName, Customer customer, Map<String, String> shopAddress, Map<String, String> settingsMap, Font tamilFont) throws DocumentException {
        // Header Title - Auction Notice
        Paragraph header = new Paragraph("AUCTION NOTICE",
                FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        // Shop Name and Address in one paragraph
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
                FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLACK));
        shopDetailsParagraph.setAlignment(Element.ALIGN_CENTER);
        
     // Add Customer Photo (if available)
        if (customer != null && customer.getPhoto() != null && customer.getPhoto().length > 0) {
            try {
                // No need to decode if it's already a byte array
                byte[] photoBytes = customer.getPhoto();

                // Try creating an iText image object directly from the byte array
                Image customerImage = Image.getInstance(photoBytes);

                // Resize the image to fit the required size
                customerImage.scaleToFit(110, 110);
                
                // Position the image on the document (top-right corner)
                customerImage.setAbsolutePosition(document.right() - 100, document.top() - 110);

                // Add the image to the document
                document.add(customerImage);
            } catch (Exception e) {
                System.err.println("Error processing customer photo: " + e.getMessage());
                e.printStackTrace();
            }
        }
//        } else {
//            System.out.println("Invalid or empty customer photo data.");
//        }

        // Add the shop details paragraph
        document.add(shopDetailsParagraph);
//
//        // Add Auction Description (if available)
//        if (auctionDescription != null && !auctionDescription.trim().isEmpty()) {
//            document.add(Chunk.NEWLINE);
//            Paragraph notice = new Paragraph(auctionDescription,
//                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK));
//            notice.setAlignment(Element.ALIGN_JUSTIFIED);
//            document.add(notice);
//        }
        
        // Add some spacing before moving to the next section
        document.add(Chunk.NEWLINE);
        
     // Add some spacing before moving to the next section
        document.add(Chunk.NEWLINE);
        
     // Auction Description from settings
        String auctionDetails = settingsUtillity.getAuctionDescription(settingsMap);

        // Add auction details (in Tamil)
        if (auctionDetails != null && !auctionDetails.trim().isEmpty()) {
            Paragraph auctionDetailsParagraph = new Paragraph(auctionDetails, tamilFont);
            auctionDetailsParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(auctionDetailsParagraph);
        }
        
        // Add some spacing before moving to the next section
        document.add(Chunk.NEWLINE);
        }
    


    private void addAuctionDetails(List<Bill> bills, Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(new float[]{1, 2, 2, 4, 1});
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
        Stream.of("Bill No", "Bill Date", "Amount", "Product Description", "Grams")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell(new Phrase(columnTitle, headFont));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setPadding(4);
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
    }

    private PdfPCell createTableCell(String content, Font font) {
        if (content == null) content = ""; // Handle null
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(4);
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
        if(customer!=null) {
        	 toCell.addElement(new Paragraph(customer.getCustomerName(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));

            // if (!customer.isFullAddress()) {
                 toCell.addElement(new Paragraph(customer.getAddress(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
//             } else {
//                 toCell.addElement(new Paragraph(customer.getAddressArea(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
//                 toCell.addElement(new Paragraph(customer.getStreetDistrict(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
//                 toCell.addElement(new Paragraph(customer.getStatePincode(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK)));
//             }
        }
       

        // Add both cells to the table
        addressTable.addCell(fromCell);
        addressTable.addCell(toCell);

        // Add the table to the document
        document.add(addressTable);
    }
}





