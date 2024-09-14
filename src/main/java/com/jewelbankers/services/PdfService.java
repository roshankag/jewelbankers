package com.jewelbankers.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jewelbankers.entity.Bill;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Optional;

@Service
public class PdfService {

    public byte[] generatePledgeBillPdf(Optional<Bill> bill) throws DocumentException {
    	Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
    	if (bill.isPresent()) {
    		
            document.open();
            document.add(new Paragraph("Pledge Bill"));
            document.add(new Paragraph("Customer: " + bill.get().getCustomer().getCustomerName()));
            document.add(new Paragraph("Details: " + bill.get().getBillSerial() + bill.get().getBillNo()));
            document.close();
            
    	}
    	return out.toByteArray();
    }

    public byte[] generateRedeemBillPdf(String customerName, String billDetails) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("Redeem Bill"));
        document.add(new Paragraph("Customer: " + customerName));
        document.add(new Paragraph("Details: " + billDetails));
        document.close();
        return out.toByteArray();
    }
}
