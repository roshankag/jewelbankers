package com.jewelbankers.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.aop.SwitchDatabase;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.repository.SettingsRepository;
import com.jewelbankers.services.AuctionPdfService;
import com.jewelbankers.services.BillService;

@RequestMapping("/jewelbankersapi")
@RestController
@SwitchDatabase
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
            String auctionDescription = settingsMap.get("AUCTION_DETAILS");

            List<Bill> bills = billService.findBillsBySearch(search, fromDate, toDate, amount, status, productTypeNo, "customername");

            // Check if bills list is empty and return a user-friendly message
            if (bills == null || bills.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No bills found for the provided search criteria.");
                return ResponseEntity.ok(response); // Return a Map<String, String> when no bills are found
            }

            Bill bill = bills.get(0);
            Map<String, String> auctionDetails = new HashMap<>();
            auctionDetails.put("billSerial", bill.getBillSerial() + String.valueOf(bill.getBillNo()));
            auctionDetails.put("billDate", bill.getBillDate().toString());
            auctionDetails.put("amount", String.valueOf(bill.getAmount()));
            auctionDetails.put("weight", String.valueOf(bill.getGrams()));
            if(bill.getCustomer() != null) {
            	  auctionDetails.put("customerName",  bill.getCustomer().getCustomerName() != null ? bill.getCustomer().getCustomerName() : "");
                  auctionDetails.put("customerAddress", bill.getCustomer().getAddress());
            }
          

            String fromAddressText = String.format("From:\n%s\n%s, %s\n%s\n%s\n%s",
                    settingsMap.get("SHOP_NAME"), 
                    settingsMap.get("SHOP_NO"), // Add SHOP_NO here
                    settingsMap.get("SHOP_STREET"),
                    settingsMap.get("SHOP_AREA"),
                    settingsMap.get("SHOP_CITY") + " - " + settingsMap.get("SHOP_PINCODE"), // Add a separator for better readability
                    settingsMap.get("SHOP_STATE"));
            
         // Extract shop address from settingsMap
            Map<String, String> shopAddress = new HashMap<>();
            shopAddress.put("SHOP_NO", settingsMap.getOrDefault("SHOP_NO", ""));
            shopAddress.put("SHOP_STREET", settingsMap.getOrDefault("SHOP_STREET", ""));
            shopAddress.put("SHOP_AREA", settingsMap.getOrDefault("SHOP_AREA", ""));
            shopAddress.put("SHOP_CITY", settingsMap.getOrDefault("SHOP_CITY", ""));
            shopAddress.put("SHOP_STATE", settingsMap.getOrDefault("SHOP_STATE", ""));
            shopAddress.put("SHOP_PINCODE", settingsMap.getOrDefault("SHOP_PINCODE", ""));


            ByteArrayInputStream pdfStream = auctionPdfService.generateAuctionPdf(
                    bills, 
                    auctionDetails, 
                    fromAddressText,
                    settingsMap.get("SHOP_NAME"), 
                    shopAddress,
                    settingsMap);

            byte[] pdfBytes = pdfStream.readAllBytes();

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=auction.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
     } finally {}
            //catch (Exception e) {
//            // Log and handle other exceptions
//            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
//        }
    }

}