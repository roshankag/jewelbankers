package com.jewelbankers.controller;

//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.itextpdf.text.DocumentException;
//import com.jewelbankers.Utility.AuctionPdfGenerator;
//import com.jewelbankers.entity.Bill;
//import com.jewelbankers.entity.Settings;
//import com.jewelbankers.services.BillService;
//import com.jewelbankers.services.SettingsService;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//public class AuctionPdfController {
//
//    private final BillService billService;
//    private final SettingsService settingsService;
//    private final AuctionPdfGenerator auctionPdfGenerator;
//
//    public AuctionPdfController(BillService billService, SettingsService settingsService, AuctionPdfGenerator auctionPdfGenerator) {
//        this.billService = billService;
//        this.settingsService = settingsService;
//        this.auctionPdfGenerator = auctionPdfGenerator;
//    }
//
//    @GetMapping("/auction/pdf")
//    public ResponseEntity<byte[]> generateAuctionPdf(@RequestParam String startDate, @RequestParam String endDate) throws DocumentException, IOException {
//        List<Bill> bills = billService.searchBillsByDateRange(startDate, endDate);
//        
//        Optional<Settings> auctionDetailsOpt = settingsService.findByParamSeq(38L);
//        Optional<Settings> sirMadamTextOpt = settingsService.findByParamSeq(36L);
//
//        if (!auctionDetailsOpt.isPresent() || !sirMadamTextOpt.isPresent()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        byte[] pdfContents = auctionPdfGenerator.generateAuctionPdf(bills, auctionDetailsOpt.get().getParamValue(), sirMadamTextOpt.get().getParamValue());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "auction.pdf");
//
//        return ResponseEntity.ok().headers(headers).body(pdfContents);
//    }
//}

//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.itextpdf.text.DocumentException;
//import com.jewelbankers.Utility.AuctionPdfGenerator;
//import com.jewelbankers.entity.Bill;
//import com.jewelbankers.entity.Settings;
//import com.jewelbankers.services.BillService;
//import com.jewelbankers.services.SettingsService;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//public class AuctionPdfController {
//
//    private final BillService billService;
//    private final SettingsService settingsService;
//    private final AuctionPdfGenerator auctionPdfGenerator;
//
//    public AuctionPdfController(BillService billService, SettingsService settingsService, AuctionPdfGenerator auctionPdfGenerator) {
//        this.billService = billService;
//        this.settingsService = settingsService;
//        this.auctionPdfGenerator = auctionPdfGenerator;
//    }
//
//    @GetMapping("/auction/pdf")
//    public ResponseEntity<byte[]> generateAuctionPdf(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Long paramSeq) throws DocumentException, IOException {
//        List<Bill> bills = billService.searchBillsByDateRange(startDate, endDate);
//        Optional<Settings> settingsOpt = settingsService.findByParamSeq(paramSeq);
//
//        if (!settingsOpt.isPresent()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        Settings settings = settingsOpt.get();
//        byte[] pdfContents = auctionPdfGenerator.generateAuctionPdf(bills, settings);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "auction.pdf");
//
//        return ResponseEntity.ok().headers(headers).body(pdfContents);
//    }
//}



//import com.itextpdf.text.DocumentException;
//import com.jewelbankers.Utility.AuctionPdfGenerator;
//import com.jewelbankers.entity.Bill;
//import com.jewelbankers.services.BillService;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//public class AuctionPdfController {
//
//    private final BillService billService;
//    private final AuctionPdfGenerator auctionPdfGenerator;
//
//    public AuctionPdfController(BillService billService, AuctionPdfGenerator auctionPdfGenerator) {
//        this.billService = billService;
//        this.auctionPdfGenerator = auctionPdfGenerator;
//    }
//
//    @GetMapping("/auction/pdf")
//    public ResponseEntity<byte[]> generateAuctionPdf(@RequestParam String startDate, @RequestParam String endDate) throws DocumentException, IOException {
//        // Retrieve the list of bills within the specified date range
//        List<Bill> bills = billService.searchBillsByDateRange(startDate, endDate);
//
//        // Generate the PDF using the AuctionPdfGenerator
//        byte[] pdfContents = auctionPdfGenerator.generateAuctionPdf(bills);
//
//        // Set up the response headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "auction.pdf");
//
//        // Return the generated PDF as a response
//        return ResponseEntity.ok().headers(headers).body(pdfContents);
//    }
//}

//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.itextpdf.text.DocumentException;
//import com.jewelbankers.Utility.AuctionPdfGenerator;
//import com.jewelbankers.entity.Bill;
//import com.jewelbankers.entity.Settings;
//import com.jewelbankers.services.BillService;
//import com.jewelbankers.services.SettingsService;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//public class AuctionPdfController {
//
//    private final BillService billService;
//    private final SettingsService settingsService;
//    private final AuctionPdfGenerator auctionPdfGenerator;
//
//    public AuctionPdfController(BillService billService, SettingsService settingsService, AuctionPdfGenerator auctionPdfGenerator) {
//        this.billService = billService;
//        this.settingsService = settingsService;
//        this.auctionPdfGenerator = auctionPdfGenerator;
//    }
//
//    @GetMapping("/auction/pdf")
//    public ResponseEntity<byte[]> generateAuctionPdf(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Long paramSeq) throws DocumentException, IOException {
//        List<Bill> bills = billService.searchBillsByDateRange(startDate, endDate);
//        Optional<Settings> settingsOpt = settingsService.findByParamSeq(paramSeq);
//
//        if (!settingsOpt.isPresent()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        byte[] pdfContents = auctionPdfGenerator.generateAuctionPdf(bills, settingsOpt.get());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "auction.pdf");
//
//        return ResponseEntity.ok().headers(headers).body(pdfContents);
//    }
//}


import com.jewelbankers.services.AuctionPdfService;
import com.jewelbankers.Utility.SettingsUtillity;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.Settings;
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
    
	@Autowired
	private SettingsUtillity settingsUtility;

    
//  http://localhost:8080/jewelbankersapi/generate-auction-pdf?search=manikandan
    @GetMapping("/generate-auction-pdf")
    public ResponseEntity<?> generatePdf(@RequestParam(value = "search", required = false) String search) {

    	Map<String,String> settingsMap = settingsUtility.convertListToMap(settingsRepository.findAll());
    	
        // Fetching bill details using the search functionality
        List<Bill> bills = billService.findBillsBySearch(search);

        if (bills == null || bills.isEmpty()) {
            return ResponseEntity.badRequest().body("No bill found for the provided search criteria.");
        }

        // Assuming we're generating the PDF for the first matched bill
        Bill bill = bills.get(0);
        
        Map<String, String> auctionDetails = new HashMap<>();
        auctionDetails.put("billSerial", bill.getBillSerial() + String.valueOf(bill.getBillNo()));
        auctionDetails.put("billDate", bill.getBillDate().toString());
        auctionDetails.put("amount", String.valueOf(bill.getAmount()));
        auctionDetails.put("weight", String.valueOf(bill.getGrams()));
        auctionDetails.put("customerName", bill.getCustomer().getCustomerName());
        auctionDetails.put("customerAddress", bill.getCustomer().getAddress());

     // Generating the PDF
        ByteArrayInputStream pdfStream = auctionPdfService.generateAuctionPdf(bills ,auctionDetails, settingsUtility.getShopDetails(settingsMap), settingsUtility.getAuctionDescription(settingsMap));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=auction.pdf");

        // Convert ByteArrayInputStream to byte array to handle the response correctly
        byte[] pdfBytes = pdfStream.readAllBytes();

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
