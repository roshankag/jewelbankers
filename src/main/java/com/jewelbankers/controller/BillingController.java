package com.jewelbankers.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jewelbankers.Utility.ErrorResponse;
import com.jewelbankers.entity.Jewel;
import com.jewelbankers.entity.JewelDetail;
import com.jewelbankers.services.BillingService;

@RestController
@RequestMapping("/jewelbankersapi/billing")
@CrossOrigin(origins = "http://localhost:4200")
public class BillingController {

    @Autowired
    private BillingService billingService;
    
    // Get all jewel bills with pagination
    @GetMapping
    public ResponseEntity<Page<Jewel>> getAllJewelBills(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "50") int size) {
        Page<Jewel> jewelBills = billingService.getAllJewelBills(page, size);
        return ResponseEntity.ok(jewelBills);
    }
    
 // Get the next available jewel number
    @GetMapping("/next-jewel-number")
    public ResponseEntity<Map<String, Integer>> getNextJewelNo() {
        int nextJewelNo = billingService.getNextJewelNo();
        Map<String, Integer> response = new HashMap<>();
        response.put("nextJewelNo", nextJewelNo);
        return ResponseEntity.ok(response);
    }
    
    // Delete jewel bill
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJewelBill(@PathVariable("id") Long id) {
        Optional<Jewel> jewelBillOptional = billingService.findById(id);
        if (jewelBillOptional.isPresent()) {
        	billingService.deleteJewelBill(id);
            return ResponseEntity.ok("Jewel Bill deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Jewel Bill not found", "Jewel Bill with id " + id + " not found"));
        }
    }
    
 // Endpoint to add a new JewelDetail based on barcode, description, quantity, item type, and weight
    @PostMapping("/addItemDetail")
    public ResponseEntity<JewelDetail> addItemDetail(@RequestBody JewelDetail jewelDetail) {
        try {
            // Call the service method to add the item details
            JewelDetail addedJewelDetail = billingService.addItemDetail(jewelDetail);

            // Return JewelDetail with status 201 (created)
            return ResponseEntity.status(HttpStatus.CREATED).body(addedJewelDetail);
        } catch (Exception e) {
            // Return a ResponseEntity with a BAD_REQUEST status in case of an error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Endpoint to fetch item details and calculate total amount, making charge, and wastage charge.
     * @param barcode The barcode of the item to fetch and calculate.
     * @param makingChargePercentage The making charge percentage provided by the client.
     * @param wastageChargePercentage The wastage charge percentage provided by the client.
     * @return ResponseEntity containing item details and calculations, or error message.
     */
    @GetMapping("/scan/{barcode}/{makingCharge}/{wastageCharge}")
    public ResponseEntity<?> getItemDetailsByBarcode(
            @PathVariable String barcode, 
            @PathVariable BigDecimal makingCharge, 
            @PathVariable BigDecimal wastageCharge) {

        // Call the service to fetch item details and perform calculations
        Map<String, Object> response = billingService.getItemDetailsAndCalculate(barcode, makingCharge, wastageCharge);

        if (response.containsKey("error")) {
            // If an error message is present, return NOT_FOUND response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.get("error"));
        } else {
            // If no errors, return OK response with calculated values
            return ResponseEntity.ok(response);
        }
    }
    
    @PostMapping(value = "/billing", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createJewelBill(
            @RequestPart("barcode") String barcode,
            @RequestPart("customerid") Long customerid,
            @RequestPart("makingChargePercent") BigDecimal makingChargePercent,
            @RequestPart("wastageChargePercent") BigDecimal wastageChargePercent,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Call the service layer to create the Jewel bill
            Jewel jewel = billingService.createJewelBill(
                    barcode,
                    customerid,
                    makingChargePercent,
                    wastageChargePercent,
                    photo);

            response.put("message", "Jewel bill successfully created for customer ID: " +
                    (jewel.getCustomer() != null ? jewel.getCustomer().getCustomerid() : "Unknown"));
            response.put("jewel", jewel);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            response.put("message", "Failed to process the provided photo.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        } catch (RuntimeException e) {
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
