package com.jewelbankers.controller;

import com.jewelbankers.entity.Sales;
import com.jewelbankers.services.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/jewelbankersapi/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    // Create new Sales
    @PostMapping
    public ResponseEntity<?> createSales(@RequestBody Sales sales) {
        try {
            Sales createdSales = salesService.saveSales(sales);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Sales record created successfully with ID: " + createdSales.getId());
        } catch (Exception e) {
        	e.printStackTrace();            
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create sales record. Error: " + e.getMessage());
        }
    }

    // Update Sales
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSales(@PathVariable Long id, @RequestBody Sales sales) {
        try {
            sales.setId(id);
            Sales updatedSales = salesService.saveSales(sales);
            return ResponseEntity.ok("Sales record updated successfully with ID: " + updatedSales.getId());
        } catch (Exception e) {
        	e.printStackTrace();    
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update sales record with ID: " + id + ". Error: " + e.getMessage());
        }
    }

    // Delete Sales
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSales(@PathVariable Long id) {
        try {
            salesService.deleteSales(id);
            return ResponseEntity.ok("Sales record deleted successfully with ID: " + id);
        } catch (Exception e) {
        	e.printStackTrace();    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete sales record with ID: " + id + ". Error: " + e.getMessage());
        }
    }

    // Get Sales by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSalesById(@PathVariable Long id) {
        try {
            Sales sales = salesService.getSalesById(id);
            if (sales != null) {
                return ResponseEntity.ok(sales);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Sales record not found for ID: " + id);
            }
        } catch (Exception e) {
        	e.printStackTrace();    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving sales record with ID: " + id + ". Error: " + e.getMessage());
        }
    }

    // Get all Sales
    @GetMapping
    public ResponseEntity<?> getAllSales() {
        try {
            List<Sales> sales = salesService.getAllSales();
            if (!sales.isEmpty()) {
                return ResponseEntity.ok(sales);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No sales records found.");
            }
        } catch (Exception e) {
        	e.printStackTrace();    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving all sales records. Error: " + e.getMessage());
        }
    }

    // Search Sales
    @GetMapping("/search")
    public ResponseEntity<?> searchSales(@RequestParam Map<String, String> searchParams) {
        try {
            List<Sales> sales = salesService.searchSales(searchParams);
            if (!sales.isEmpty()) {
                return ResponseEntity.ok(sales);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No sales records match the search criteria.");
            }
        } catch (Exception e) {
        	e.printStackTrace();    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error searching for sales records. Error: " + e.getMessage());
        }
    }
}
