package com.jewelbankers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jewelbankers.entity.SupplierPayments;
import com.jewelbankers.services.SupplierPaymentDetailService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/jewelbankersapi/supplier/payments")
public class SupplierPaymentDetailController {

    @Autowired
    private SupplierPaymentDetailService supplierPaymentDetailService;

    @GetMapping
    public ResponseEntity<?> getAllSupplierPaymentDetails() {
        try {
            List<SupplierPayments> details = supplierPaymentDetailService.getAllSupplierPaymentDetails();
            if (details.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No supplier payment details found.");
            }
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving supplier payment details: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierPaymentDetailById(@PathVariable Integer id) {
        try {
        	SupplierPayments detail = supplierPaymentDetailService.getSupplierPaymentDetailById(id);
            if (detail != null) {
                return ResponseEntity.ok(detail);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Supplier payment detail not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error retrieving supplier payment detail: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createSupplierPaymentDetail(@RequestBody SupplierPayments detail) {
        try {
            SupplierPayments createdDetail = supplierPaymentDetailService.saveSupplierPaymentDetail(detail);
            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body("Supplier payment detail created successfully: " + createdDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error creating supplier payment detail: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplierPaymentDetail(@PathVariable Long id, @RequestBody SupplierPayments detail) {
        try {
            detail.setId(id);
            SupplierPayments updatedDetail = supplierPaymentDetailService.saveSupplierPaymentDetail(detail);
            return ResponseEntity.ok("Supplier payment detail updated successfully: " + updatedDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error updating supplier payment detail: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplierPaymentDetail(@PathVariable Integer id) {
        try {
            supplierPaymentDetailService.deleteSupplierPaymentDetail(id);
            return ResponseEntity.status(HttpStatus.OK).body("Supplier payment detail deleted successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error deleting supplier payment detail: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchSupplierPaymentDetails(@RequestParam Map<String, String> search) {
        try {
            List<SupplierPayments> details = supplierPaymentDetailService.searchSupplierPaymentDetails(search);
            if (details.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No supplier payment details match the search criteria.");
            }
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error searching for supplier payment details: " + e.getMessage());
        }
    }
}
