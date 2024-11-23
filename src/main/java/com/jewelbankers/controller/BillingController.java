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
            // Log the incoming jewelDetail to ensure it's parsed correctly
            System.out.println("Received JewelDetail: " + jewelDetail);

            JewelDetail addedJewelDetail = billingService.addItemDetail(jewelDetail);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedJewelDetail);
        } catch (Exception e) {
            // Log the error for debugging
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    /**
     * Endpoint to fetch item details and calculate total amount, making charge, and wastage charge.
     * @param barcode The barcode of the item to fetch.
     * @return ResponseEntity containing item details and calculations, or error message.
     */
    @GetMapping("/scan/{barcode}")
    public ResponseEntity<?> getItemDetailsByBarcode(
            @PathVariable String barcode) {

        // Call the service to fetch item details 
        Map<String, Object> response = billingService.getItemDetails(barcode);

        if (response.containsKey("error")) {
            // If an error message is present, return NOT_FOUND response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.get("error"));
        } else {
            // If no errors, return OK response 
            return ResponseEntity.ok(response);
        }
    }
    
   
    
    

}