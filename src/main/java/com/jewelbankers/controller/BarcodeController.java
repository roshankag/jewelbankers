//package com.jewelbankers.controller;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.jewelbankers.entity.Barcode;
//import com.jewelbankers.services.BarcodeService;
//
//import jakarta.persistence.EntityNotFoundException;
//
//@RestController
//@RequestMapping("/jewelbankersapi/barcodes")
//@CrossOrigin(origins = "http://localhost:4200")
//public class BarcodeController {
//
//    @Autowired
//    private BarcodeService barcodeService;
//
//    @PostMapping("/create")
//    public ResponseEntity<?> createBarcode(@RequestBody Barcode barcode) {
//        if (barcode == null) {
//            return ResponseEntity.badRequest().body("Request body is empty. Please provide barcode details.");
//        }
//
//        try {
//        	// Validate input barcode fields (e.g., required fields like weight, purity, etc.)
//        	if (barcode.getItemType().getItemtypeno() == null || 
//        	    barcode.getGrossWeight() == null || barcode.getGrossWeight().compareTo(BigDecimal.ZERO) == 0 || 
//        	    barcode.getPurity() == null || barcode.getPurity().compareTo(BigDecimal.ZERO) == 0) {
//        	    
//        	    return ResponseEntity.badRequest().body("Missing required barcode details. Ensure item type, weight, and purity are provided.");
//        	}
//
//
//            Barcode createdBarcode = barcodeService.createBarcode(barcode);
//            
//            // If barcode creation fails or returns null, return a bad request response
//            if (createdBarcode == null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Barcode creation failed. Please verify the input details.");
//            }
//
//            return ResponseEntity.ok("Barcode created successfully with ID: " + createdBarcode.getId());
//
//        } catch (DataIntegrityViolationException e) {
//            return ResponseEntity.badRequest()
//                    .body("Invalid data provided: " + e.getMostSpecificCause().getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while creating barcode: " + e.getMessage());
//        }
//    }
//
//    
//    @PostMapping("/bulk-create")
//    public ResponseEntity<?> bulkCreateBarcodes(@RequestBody List<Barcode> barcodes) {
//        if (barcodes == null || barcodes.isEmpty()) {
//            return ResponseEntity.badRequest().body("Request body is empty. Please provide barcode details.");
//        }
//
//        try {
//            List<Barcode> createdBarcodes = barcodeService.bulkCreateBarcodes(barcodes);
//
//            if (createdBarcodes.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NO_CONTENT)
//                        .body("No barcodes were created. Please verify the input details.");
//            }
//
//            return ResponseEntity.ok("Bulk barcode creation successful. " + createdBarcodes.size() + " barcodes created.");
//        } catch (DataIntegrityViolationException e) {
//            return ResponseEntity.badRequest()
//                    .body("Invalid data provided: " + e.getMostSpecificCause().getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while creating barcodes: " + e.getMessage());
//        }
//    }
//    
//    @GetMapping("/print/{tagNo}")
//    public ResponseEntity<?> printBarcode(@PathVariable String tagNo) {
//        try {
//            // Call service to print the barcode and generate JPG/PDF
//            String printStatus = barcodeService.printBarcode(tagNo);
//
//            if (printStatus.contains("Error")) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barcode not found for tag: " + tagNo);
//            }
//
//            return ResponseEntity.ok("Barcode print successful. Details: " + printStatus);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while printing barcode for tag " + tagNo + ": " + e.getMessage());
//        }
//    }
//
//    
//    @GetMapping("/print/bulk")
//    public ResponseEntity<?> printBulkBarcodes(@RequestParam List<String> tagNos) {
//        try {
//            // Call the service method to process bulk printing
//            List<String> responseMessages = barcodeService.printBulkBarcodes(tagNos);
//
//            // Check if all barcodes were processed successfully
//            if (responseMessages.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No barcodes found for the provided tags.");
//            }
//
//            // Return the response messages for bulk printing
//            return ResponseEntity.ok("Bulk print initiated successfully. Details: " + responseMessages);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while printing barcodes: " + e.getMessage());
//        }
//    }
//    
//    @GetMapping("/filter")
//    public ResponseEntity<?> fetchBarcodesByFullSearch(
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
//            @RequestParam(required = false) Integer itemTypeNo) {
//        try {
//            // Fetch the filtered barcodes from the service
//            List<Barcode> filteredBarcodes = barcodeService.fetchBarcodesByFullsearch(startDate, endDate, itemTypeNo);
//
//            // Check if no barcodes were found based on the provided filters
//            if (filteredBarcodes.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("No barcodes found for the provided filters. Please verify your criteria.");
//            }
//
//            // Return a message when barcodes are found, along with the data
//            return ResponseEntity.ok(
//                    Map.of("message", "Barcodes found successfully!", "barcodes", filteredBarcodes)
//            );
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while fetching barcodes: " + e.getMessage());
//        }
//    }
//    
//    @GetMapping("/search")
//    public ResponseEntity<?> fetchBarcodesBySearch(
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
//            @RequestParam(required = false) Integer itemTypeNo) {
//        try {
//            // Call service to fetch filtered barcodes
//            List<Barcode> filteredBarcodes = barcodeService.fetchBarcodesByFilters(startDate, endDate, itemTypeNo);
//
//            if (filteredBarcodes.isEmpty()) {
//                // If no barcodes found for the provided filters, return a custom message
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("No barcodes found for the provided filters.");
//            }
//
//            // If barcodes are found, return them with a success message
//            return ResponseEntity.ok()
//                    .body("Barcodes found successfully: " + filteredBarcodes.size() + " barcode(s) matched the filters.");
//        } catch (Exception e) {
//            // Handle unexpected errors and return a message
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while fetching barcodes: " + e.getMessage());
//        }
//    }
//    
//    @PutMapping("/updateByTag/{tag}")
//    public ResponseEntity<?> updateBarcodeByTag(
//            @PathVariable String tag,
//            @RequestBody Barcode updatedBarcode) {
//        try {
//            // Call service to update the barcode
//            Barcode savedBarcode = barcodeService.updateBarcodeByTag(tag, updatedBarcode);
//
//            return ResponseEntity.ok(Map.of(
//                    "message", "Barcode updated successfully!",
//                    "barcode", savedBarcode
//            ));
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Barcode not found with tag: " + tag);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while updating barcode: " + e.getMessage());
//        }
//    }
//    
//    @DeleteMapping("/deleteByTag/{tag}")
//    public ResponseEntity<?> deleteBarcodeByTag(@PathVariable String tag) {
//        try {
//            // Call service to delete the barcode
//            barcodeService.deleteBarcodeByTag(tag);
//
//            return ResponseEntity.ok(Map.of(
//                    "message", "Barcode deleted successfully!",
//                    "tag", tag
//            ));
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Barcode not found with tag: " + tag);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while deleting barcode: " + e.getMessage());
//        }
//    }
//
//
//
//
//
//
//
//}
//
