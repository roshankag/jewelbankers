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
import java.time.LocalDate;
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
    public ResponseEntity<?> generatePdf(
    		@RequestParam(value = "search", required = false) String search,
    		@RequestParam(value = "fromDate", required = false) String fromDateStr,
            @RequestParam(value = "toDate", required = false) String toDateStr,
            @RequestParam(value = "amount", required = false) Integer amount,
            @RequestParam(value = "status", required = false) Character status,
            @RequestParam(value = "productTypeNo", required = false) Integer productTypeNo) throws IOException {
     try {
    	 
    	// Parse date parameters
         LocalDate fromDate = fromDateStr != null ? LocalDate.parse(fromDateStr) : null;
         LocalDate toDate = toDateStr != null ? LocalDate.parse(toDateStr) : null;
         
        Map<String, String> settingsMap = settingsRepository.findAll().stream()
                .collect(HashMap::new, (m, v) -> m.put(v.getParamId(), v.getParamValue()), HashMap::putAll);

        // Fetching specific settings by paramId
       // String shopName = ;
        String auctionDescription = settingsMap.get("AUCTION_DETAILS");
        
        

//        if (shopName == null || auctionDescription == null) {
//            throw new ResourceNotFoundException("Required settings not found.");
//        }

        List<Bill> bills =billService.findBillsBySearch(search, fromDate, toDate, amount, status, productTypeNo, "customername");

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
        
        String fromAddressText = String.format("From:\n%s\n%s\n%s\n%s\n%s", 
        		settingsMap.get("SHOP_NAME"), settingsMap.get("SHOP_STREET"), settingsMap.get("SHOP_AREA"), settingsMap.get("SHOP_CITY")+settingsMap.get("SHOP_PINCODE"), settingsMap.get("SHOP_STATE"));
        
        

        ByteArrayInputStream pdfStream = auctionPdfService.generateAuctionPdf(
                bills, auctionDetails, fromAddressText, auctionDescription, settingsMap.get("SHOP_NAME"));

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=auction.pdf");

        byte[] pdfBytes = pdfStream.readAllBytes();
        
     // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=auction.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
    catch (ResourceNotFoundException e) {
    	// Handle specific application exceptions
        return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
    } catch (Exception e) {
        // Log and handle other exceptions
        return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
    }
}
}
