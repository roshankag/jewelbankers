package com.jewelbankers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jewelbankers.entity.SupplierWeight;
import com.jewelbankers.services.SupplierWeightService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/jewelbankersapi/supplier-weights")
public class SupplierWeightController {

    @Autowired
    private SupplierWeightService supplierWeightService;

    @GetMapping
    public ResponseEntity<?> getAllSupplierWeights() {
        try {
            List<SupplierWeight> supplierWeights = supplierWeightService.getAllSupplierWeights();
            if (supplierWeights.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No supplier weights found.");
            }
            return ResponseEntity.ok(supplierWeights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving supplier weights: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierWeightById(@PathVariable Long id) {
        try {
            SupplierWeight supplierWeight = supplierWeightService.getSupplierWeightById(id);
            if (supplierWeight != null) {
                return ResponseEntity.ok(supplierWeight);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Supplier weight not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving supplier weight: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createSupplierWeight(@RequestBody SupplierWeight supplierWeight) {
        try {
            SupplierWeight createdSupplierWeight = supplierWeightService.saveSupplierWeight(supplierWeight);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplierWeight);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error creating supplier weight: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplierWeight(@PathVariable Long id) {
        try {
            supplierWeightService.deleteSupplierWeight(id);
            return ResponseEntity.status(HttpStatus.OK).body("Supplier weight deleted successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting supplier weight: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchSupplierWeights(@RequestParam Map<String, String> search) {
        try {
            List<SupplierWeight> supplierWeights = supplierWeightService.searchSupplierWeights(search);
            if (supplierWeights.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No supplier weights match the search criteria.");
            }
            return ResponseEntity.ok(supplierWeights);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error searching supplier weights: " + e.getMessage());
        }
    }
}
