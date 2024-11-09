package com.jewelbankers.controller;

import com.jewelbankers.entity.Bill;
import com.jewelbankers.exception.ResourceNotFoundException;
import com.jewelbankers.repository.SettingsRepository;
import com.jewelbankers.services.BillPdfService;
import com.jewelbankers.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/jewelbankersapi")
@RestController
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
            @RequestParam(value = "productTypeNo", required = false) Integer productTypeNo) throws IOException {
        try {
            // Parse date parameters
            LocalDate fromDate = fromDateStr != null ? LocalDate.parse(fromDateStr) : null;
            LocalDate toDate = toDateStr != null ? LocalDate.parse(toDateStr) : null;

            Map<String, String> settingsMap = settingsRepository.findAll().stream()
                    .collect(HashMap::new, (m, v) -> m.put(v.getParamId(), v.getParamValue()), HashMap::putAll);

            // Fetch bills based on search criteria
            List<Bill> bills = billService.findBillsBySearch(search, fromDate, toDate, amount, status, productTypeNo, "customername");

            // Check if bills list is empty and return a user-friendly message
            if (bills == null || bills.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No bills found for the provided search criteria.");
                return ResponseEntity.ok(response);
            }

            // Generate the PDF for the bills
            ByteArrayInputStream pdfStream = billPdfService.generateBillPdf(bills);

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
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}
