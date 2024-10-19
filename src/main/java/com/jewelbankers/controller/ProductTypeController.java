package com.jewelbankers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jewelbankers.entity.ProductType;
import com.jewelbankers.services.ProductTypeService;
import com.jewelbankers.Utility.ErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/jewelbankersapi/product_types")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping
    public ResponseEntity<?> getAllProductTypes() {
        try {
            List<ProductType> productTypes = productTypeService.getAllProductTypes();
            return ResponseEntity.ok(productTypes);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(
                "Error fetching product types",
                e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductTypeById(@PathVariable("id") Long id) {
        try {
            ProductType productType = productTypeService.getProductTypeById(id);
            if (productType != null) {
                return ResponseEntity.ok(productType);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(
                    "Product Type not found",
                    "Product Type with ID " + id + " not found"
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(
                "Error fetching product type",
                e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> createProductType(@RequestBody ProductType productType) {
        try {
            ProductType createdProductType = productTypeService.createProductType(productType);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProductType);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(
                "Error creating product type",
                e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductType(@PathVariable("id") Long id, @RequestBody ProductType productTypeDetails) {
        try {
            ProductType updatedProductType = productTypeService.updateProductType(id, productTypeDetails);
            if (updatedProductType != null) {
                return ResponseEntity.ok(updatedProductType);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(
                    "Product Type not found",
                    "Product Type with ID " + id + " not found for update"
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(
                "Error updating product type",
                e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductType(@PathVariable("id") Long id) {
        try {
            ProductType productType = productTypeService.getProductTypeById(id);
            if (productType != null) {
                productTypeService.deleteProductType(id);
                return ResponseEntity.ok("Product Type with ID " + id + " deleted successfully");
            } else {
                ErrorResponse errorResponse = new ErrorResponse(
                    "Product Type not found",
                    "Product Type with ID " + id + " is already deleted or does not exist"
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(
                "Error deleting product type",
                e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
