package com.jewelbankers.controller;

import com.jewelbankers.entity.PurchaseItems;
import com.jewelbankers.services.PurchaseItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/jewelbankersapi/purchase/items")
public class PurchaseItemsController {

    @Autowired
    private PurchaseItemsService purchaseItemsService;

    // Create new PurchaseItems
    @PostMapping
    public ResponseEntity<?> createPurchaseItems(@RequestBody PurchaseItems purchaseItems) {
        try {
            PurchaseItems createdPurchaseItems = purchaseItemsService.savePurchaseItems(purchaseItems);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body("Purchase item created successfully with ID: " + createdPurchaseItems.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error creating purchase item: " + e.getMessage());
        }
    }

    // Update PurchaseItems
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePurchaseItems(@PathVariable Long id, @RequestBody PurchaseItems purchaseItems) {
        try {
            purchaseItems.setId(id);
            PurchaseItems updatedPurchaseItems = purchaseItemsService.savePurchaseItems(purchaseItems);
            return ResponseEntity.ok("Purchase item updated successfully with ID: " + updatedPurchaseItems.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error updating purchase item: " + e.getMessage());
        }
    }

    // Delete PurchaseItems
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePurchaseItems(@PathVariable Long id) {
        try {
            purchaseItemsService.deletePurchaseItems(id);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body("Purchase item deleted successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting purchase item: " + e.getMessage());
        }
    }

    // Get PurchaseItems by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPurchaseItemsById(@PathVariable Long id) {
        try {
            PurchaseItems purchaseItems = purchaseItemsService.getPurchaseItemsById(id);
            if (purchaseItems != null) {
                return ResponseEntity.ok(purchaseItems);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Purchase item not found for ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving purchase item with ID " + id + ": " + e.getMessage());
        }
    }

    // Get all PurchaseItems
    @GetMapping
    public ResponseEntity<?> getAllPurchaseItems() {
        try {
            List<PurchaseItems> purchaseItems = purchaseItemsService.getAllPurchaseItems();
            if (purchaseItems.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                     .body("No purchase items found.");
            }
            return ResponseEntity.ok(purchaseItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving purchase items: " + e.getMessage());
        }
    }

    // Search PurchaseItems based on criteria
    @GetMapping("/search")
    public ResponseEntity<?> searchPurchaseItems(@RequestParam Map<String, String> search) {
        try {
            List<PurchaseItems> purchaseItems = purchaseItemsService.searchPurchaseItems(search);
            if (purchaseItems.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                     .body("No purchase items match the search criteria.");
            }
            return ResponseEntity.ok(purchaseItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error searching for purchase items: " + e.getMessage());
        }
    }
}
