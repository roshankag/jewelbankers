package com.jewelbankers.controller;

import com.jewelbankers.entity.SalesItems;
import com.jewelbankers.services.SalesItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/jewelbankersapi/salesitems")
public class SalesItemsController {

    @Autowired
    private SalesItemsService salesItemsService;

    // Create Sales Item
    @PostMapping
    public ResponseEntity<?> createSalesItem(@RequestBody SalesItems salesItem) {
        try {
            SalesItems createdItem = salesItemsService.saveSalesItem(salesItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                "Sales item created successfully with ID: " + createdItem.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "Error creating sales item: " + e.getMessage());
        }
    }

    // Get All Sales Items
    @GetMapping
    public ResponseEntity<?> getAllSalesItems() {
        try {
            List<SalesItems> items = salesItemsService.getAllSalesItems();
            if (items.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    "No sales items available.");
            }
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Error retrieving sales items: " + e.getMessage());
        }
    }

    // Get Sales Item by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSalesItemById(@PathVariable Long id) {
        try {
            SalesItems item = salesItemsService.getSalesItemById(id);
            if (item != null) {
                return ResponseEntity.ok(item);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Sales item not found for ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Error retrieving sales item: " + e.getMessage());
        }
    }

    // Update Sales Item
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSalesItem(@PathVariable Long id, @RequestBody SalesItems salesItem) {
        try {
            salesItem.setId(id);
            SalesItems updatedItem = salesItemsService.saveSalesItem(salesItem);
            return ResponseEntity.ok("Sales item updated successfully with ID: " + updatedItem.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "Error updating sales item: " + e.getMessage());
        }
    }

    // Delete Sales Item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalesItem(@PathVariable Long id) {
        try {
            salesItemsService.deleteSalesItem(id);
            return ResponseEntity.ok("Sales item deleted successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Error deleting sales item: " + e.getMessage());
        }
    }

    // Search Sales Items
    @GetMapping("/search")
    public ResponseEntity<?> searchSalesItems(@RequestParam Map<String, String> searchParams) {
        try {
            List<SalesItems> salesItems = salesItemsService.searchSalesItems(searchParams);
            if (salesItems.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    "No sales items match the search criteria.");
            }
            return ResponseEntity.ok(salesItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Error searching for sales items: " + e.getMessage());
        }
    }
}
