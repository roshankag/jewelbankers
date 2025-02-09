package com.jewelbankers.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.entity.Purchase;
import com.jewelbankers.services.PurchaseService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/jewelbankersapi/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    // Create new Purchase
    @PostMapping
    public ResponseEntity<?> createPurchase(@RequestBody Purchase purchase) {
        try {
            Purchase createdPurchase = purchaseService.savePurchase(purchase);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Purchase created successfully with ID: " + createdPurchase.getId());
        } catch (Exception e) {
            e.printStackTrace(); // Print full stack trace for debugging
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating purchase: " + e.getMessage());
        }
    }


    // Update Purchase
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePurchase(@PathVariable Long id, @RequestBody Purchase purchase) {
        try {
            purchase.setId(id);
            Purchase updatedPurchase = purchaseService.savePurchase(purchase);
            return ResponseEntity.ok("Purchase updated successfully with ID: " + updatedPurchase.getId());
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error updating purchase: " + e.getMessage());
        }
    }

    // Delete Purchase
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePurchase(@PathVariable Long id) {
        try {
            purchaseService.deletePurchase(id);
            return ResponseEntity.status(HttpStatus.OK).body("Purchase deleted successfully with ID: " + id);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting purchase: " + e.getMessage());
        }
    }

    // Get Purchase by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPurchaseById(@PathVariable Long id) {
        try {
            Purchase purchase = purchaseService.getPurchaseById(id);
            if (purchase != null) {
                return ResponseEntity.ok(purchase);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Purchase not found for ID: " + id);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving purchase: " + e.getMessage());
        }
    }

    // Get all Purchases
    @GetMapping
    public ResponseEntity<?> getAllPurchases() {
        try {
            Iterable<Purchase> purchases = purchaseService.getAllPurchases();
            if (((Collection<?>) purchases).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No purchases found.");
            }
            return ResponseEntity.ok(purchases);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving purchases: " + e.getMessage());
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> searchPurchases(@RequestParam Map<String, String> search) {
        try {
            List<Purchase> purchases = purchaseService.searchPurchases(search);
            if (purchases.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No purchases match the search criteria.");
            }
            return ResponseEntity.ok(purchases);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error searching for purchases: " + e.getMessage());
        }
    }

}
