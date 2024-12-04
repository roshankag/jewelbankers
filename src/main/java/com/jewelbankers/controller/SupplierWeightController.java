package com.jewelbankers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.entity.SupplierWeight;
import com.jewelbankers.services.SupplierWeightService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/jewelbankersapi/supplier-weights")
public class SupplierWeightController {

    @Autowired
    private SupplierWeightService supplierWeightService;

    @GetMapping
    public List<SupplierWeight> getAllSupplierWeights() {
        return supplierWeightService.getAllSupplierWeights();
    }

    @GetMapping("/{id}")
    public SupplierWeight getSupplierWeightById(@PathVariable Long id) {
        return supplierWeightService.getSupplierWeightById(id);
    }

    @PostMapping
    public SupplierWeight createSupplierWeight(@RequestBody SupplierWeight supplierWeight) {
        return supplierWeightService.saveSupplierWeight(supplierWeight);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplierWeight(@PathVariable Long id) {
        supplierWeightService.deleteSupplierWeight(id);
    }
    
    @GetMapping("/search")
    public List<SupplierWeight> searchSupplierWeights(@RequestParam Map<String, String> searchParams) {
        return supplierWeightService.searchSupplierWeights(searchParams);
    }
}
