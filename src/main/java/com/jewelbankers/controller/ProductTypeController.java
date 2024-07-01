package com.jewelbankers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jewelbankers.entity.ProductType;
import com.jewelbankers.services.ProductTypeService;

import java.util.List;

@RestController
@RequestMapping("/product_types")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping
    public List<ProductType> getAllProductTypes() {
        return productTypeService.getAllProductTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductType> getProductTypeById(@PathVariable("id") Long id) {
        ProductType productType = productTypeService.getProductTypeById(id);
        if (productType != null) {
            return ResponseEntity.ok(productType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ProductType createProductType(@RequestBody ProductType productType) {
        return productTypeService.createProductType(productType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductType> updateProductType(@PathVariable("id") Long id, @RequestBody ProductType productTypeDetails) {
        ProductType updatedProductType = productTypeService.updateProductType(id, productTypeDetails);
        if (updatedProductType != null) {
            return ResponseEntity.ok(updatedProductType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductType(@PathVariable("id") Long id) {
        ProductType productType = productTypeService.getProductTypeById(id);
        if (productType != null) {
            productTypeService.deleteProductType(id);
            return ResponseEntity.ok("Product Type with ID " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Product Type with ID " + id + " is already deleted or does not exist");
        }
    }
}
