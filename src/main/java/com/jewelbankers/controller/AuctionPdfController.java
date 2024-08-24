package com.jewelbankers.controller;

import com.jewelbankers.exception.ResourceNotFoundException;
import com.jewelbankers.services.AuctionPdfService;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.repository.SettingsRepository;
import com.jewelbankers.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuctionPdfController {

    @Autowired
    private AuctionPdfService auctionPdfService;

    @Autowired
    private BillService billService;
    
    @Autowired
    private SettingsRepository settingsRepository;
    
    @GetMapping("/generate-auction-pdf")
    public ResponseEntity<?> generatePdf(@RequestParam(value = "search", required = false) String search) throws IOException {

        Map<String, String> settingsMap = settingsRepository.findAll().stream()
                .collect(HashMap::new, (m, v) -> m.put(v.getParamSeq().toString(), v.getParamValue()), HashMap::putAll);

        List<Bill> bills = billService.findBillsBySearch(search);

        if (bills == null || bills.isEmpty()) {
            throw new ResourceNotFoundException("No bill found for the provided search criteria.");
        }

        Bill bill = bills.get(0);
        Map<String, String> auctionDetails = new HashMap<>();
        auctionDetails.put("billSerial", bill.getBillSerial() + String.valueOf(bill.getBillNo()));
        auctionDetails.put("billDate", bill.getBillDate().toString());
        auctionDetails.put("amount", String.valueOf(bill.getAmount()));
        auctionDetails.put("weight", String.valueOf(bill.getGrams()));
        auctionDetails.put("customerName", bill.getCustomer().getCustomerName());
        auctionDetails.put("customerAddress", bill.getCustomer().getAddress());

        ByteArrayInputStream pdfStream = auctionPdfService.generateAuctionPdf(bills, auctionDetails, settingsMap.get("shopDetails"), settingsMap.get("auctionDescription"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=auction.pdf");

        byte[] pdfBytes = pdfStream.readAllBytes();

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
