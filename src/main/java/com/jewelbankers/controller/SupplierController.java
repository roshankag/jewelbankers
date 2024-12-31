package com.jewelbankers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jewelbankers.entity.Supplier;
import com.jewelbankers.services.SupplierService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/jewelbankersapi/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> getAllSuppliers() {
        try {
            List<Supplier> suppliers = supplierService.getAllSuppliers();
            if (suppliers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No suppliers found.");
            }
            return ResponseEntity.ok(suppliers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving suppliers: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable Long id) {
        try {
            Supplier supplier = supplierService.getSupplierById(id);
            if (supplier != null) {
                return ResponseEntity.ok(supplier);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Supplier not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving supplier: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createSupplier(@RequestBody Supplier supplier) {
        try {
            Supplier createdSupplier = supplierService.saveSupplier(supplier);
            // Return a success message with the created supplier data
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body("Supplier created successfully: " + createdSupplier);
        } catch (Exception e) {
            // Return a failure message with the exception details
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error creating supplier: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        try {
            supplier.setId(id);
            Supplier updatedSupplier = supplierService.saveSupplier(supplier);
            // Return a success message with the updated supplier data
            return ResponseEntity.ok("Supplier updated successfully: " + updatedSupplier);
        } catch (Exception e) {
            // Return a failure message with the exception details
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error updating supplier: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {
        try {
            supplierService.deleteSupplier(id);
            return ResponseEntity.status(HttpStatus.OK).body("Supplier deleted successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting supplier: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchSuppliers(@RequestParam Map<String, String> search) {
        try {
            List<Supplier> suppliers = supplierService.searchSuppliers(search);
            if (suppliers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                     .body("There is no supplier matching the search criteria.");
            }
            return ResponseEntity.ok(suppliers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error searching for suppliers: " + e.getMessage());
        }
    }

}
