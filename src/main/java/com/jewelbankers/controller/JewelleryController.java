//package com.jewelbankers.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.jewelbankers.entity.Jewellery;
//import com.jewelbankers.services.JewelleryService;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/jewellery")
//public class JewelleryController {
//
//	@Autowired
//    private JewelleryService jewelleryService;
//
//	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Map<String, Object>> createJewellery(@RequestBody Jewellery jewellery) {
//	    try {
//	        // Log the incoming jewellery data
//	        System.out.println("Received jewellery data: " + jewellery);
//
//	        // Delegate jewellery creation to the service layer
//	        Jewellery createdJewellery = jewelleryService.saveJewellery(jewellery); // Pass jewellery without photo
//
//	        // Log the created jewellery
//	        System.out.println("Created jewellery: " + createdJewellery);
//
//	        // Create a response map to hold both the message and the jewellery
//	        Map<String, Object> response = new HashMap<>();
//	        response.put("message", "Jewellery bill successfully created with invoiceNo: " + createdJewellery.getInvoiceNo());
//	        response.put("jewellery", createdJewellery);
//
//	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//	    } catch (Exception e) {
//	        // Log the exception
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	    }
//	}
//	
//	@PutMapping(value = "/update/{invoiceSeq}", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Map<String, Object>> updateJewellery(
//	        @PathVariable Long invoiceSeq,
//	        @RequestBody Jewellery jewellery) {
//	    try {
//	        // Validate the Jewellery object
//	        if (jewellery == null) {
//	            return ResponseEntity.badRequest().body(null);
//	        }
//
//	        // Call the service to update the jewellery
//	        Jewellery updatedJewellery = jewelleryService.updateJewellery(invoiceSeq, jewellery);
//
//	        // Create a response map to hold both the message and the updated jewellery
//	        Map<String, Object> response = new HashMap<>();
//	        response.put("message", "Jewellery updated successfully with customerId: " + updatedJewellery.getCustomer().getCustomerid());
//	        response.put("jewellery", updatedJewellery);
//
//	        return ResponseEntity.ok(response);
//	    } catch (Exception e) {
//	        // Log the exception (optional)
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	    }
//	}
//	
//	@GetMapping("/next-invoice-number")
//	public ResponseEntity<Map<String, Integer>> getNextInvoiceNo() {
//	    int nextInvoiceNo = jewelleryService.getNextInvoiceNo(); // Assuming you have a service for jewellery
//	    Map<String, Integer> response = new HashMap<>();
//	    response.put("nextInvoiceNo", nextInvoiceNo);
//	    return ResponseEntity.ok(response);
//	}
//	
//	// Endpoint for full search of jewellery items
//    @GetMapping("/fullsearch")
//    public ResponseEntity<?> searchJewellery(
//            @RequestParam(required = false) String search,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
//            @RequestParam(required = false) String itemDescription,
//            @RequestParam(required = false) Double price,
//            @RequestParam(required = false) String sortOrder) {
//
//        // Call the service method to find jewellery based on search criteria
//        List<Jewellery> jewelleryBills = jewelleryService.findJewelleryBySearch(search, fromDate, toDate, itemDescription, price, sortOrder);
//
//        // Check if no jewellery items were found
//        if (jewelleryBills.isEmpty()) {
//            // Return a 200 OK response with a message indicating no jewellery bills were found
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "No jewellery found with the provided search criteria.");
//            return ResponseEntity.ok(response); // Return a response with no jewellery
//        }
//        
//        // Return the list of jewellery items with a 200 OK status
//        return ResponseEntity.ok(jewelleryBills);
//    }
//
//}
