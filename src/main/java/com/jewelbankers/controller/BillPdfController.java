package com.jewelbankers.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.aop.SwitchDatabase;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.repository.SettingsRepository;
import com.jewelbankers.services.BillPdfService;
import com.jewelbankers.services.BillService;

@RequestMapping("/jewelbankersapi")
@RestController
@SwitchDatabase
public class BillPdfController {

    @Autowired
    private BillPdfService billPdfService;

    @Autowired
    private BillService billService;

    @Autowired
    private SettingsRepository settingsRepository;

    @GetMapping("/generate-bill-pdf")
    public ResponseEntity<?> generateBillPdf(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "fromDate", required = false) String fromDateStr,
            @RequestParam(value = "toDate", required = false) String toDateStr,
            @RequestParam(value = "amount", required = false) Integer amount,
            @RequestParam(value = "status", required = false) Character status,
            @RequestParam(value = "productTypeNo", required = false) Integer productTypeNo,
            @RequestParam(value = "phoneno", required = false) Long phoneno) throws IOException {
        try {
            // Parse date parameters
            LocalDate fromDate = null;
            LocalDate toDate = null;
            try {
                if (fromDateStr != null) fromDate = LocalDate.parse(fromDateStr);
                if (toDateStr != null) toDate = LocalDate.parse(toDateStr);
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body("Invalid date format. Please use 'yyyy-MM-dd'.");
            }

            Map<String, String> settingsMap = settingsRepository.findAll().stream()
                    .collect(HashMap::new, (m, v) -> m.put(v.getParamId(), v.getParamValue()), HashMap::putAll);

            // Fetch bills based on search criteria
            List<Bill> bills = billService.findBillsBySearch(search, fromDate, toDate, amount, status, productTypeNo, "customername", phoneno);

            // Check if bills list is empty and return a user-friendly message
            if (bills == null || bills.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No bills found for the provided search criteria.");
                return ResponseEntity.ok(response);
            }
            
            // Generate the PDF for the bills
            ByteArrayInputStream pdfStream = billPdfService.generateBillPdf(bills, settingsMap.get("SHOP_NAME"), settingsMap);

            byte[] pdfBytes = pdfStream.readAllBytes();

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=bill.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            // Log and handle other exceptions
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}