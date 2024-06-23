package com.jewelbankers.controller;

import com.jewelbankers.entity.Bill;
import com.jewelbankers.services.BillService;

import ch.qos.logback.classic.Level;
import ch.qos.logback.core.util.Loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bills")
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBillById(@PathVariable("id") Integer id) {
        Optional<Bill> billOptional = billService.findById(id);
        if (billOptional.isPresent()) {
            return ResponseEntity.ok(billOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill with id " + id + " not found");
        }
    }


    @PostMapping
    public Bill createBill(@RequestBody Bill bill) {
        return billService.saveBill(bill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBillByBillNo(@PathVariable("id") Integer id, @RequestBody Bill billDetails) {
        Optional<Bill> billOptional = billService.findById(id);
        if (!billOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Bill existingBill = billOptional.get();
        
        // Update fields individually
        if (billDetails.getBillSerial() != null) existingBill.setBillSerial(billDetails.getBillSerial());
        if (billDetails.getBillNo() != null) existingBill.setBillNo(billDetails.getBillNo());
        if (billDetails.getBillDate() != null) existingBill.setBillDate(billDetails.getBillDate());
        if (billDetails.getCustomerId() != null) existingBill.setCustomerId(billDetails.getCustomerId());
        if (billDetails.getCareOf() != null) existingBill.setCareOf(billDetails.getCareOf());
        if (billDetails.getProductTypeNo() != null) existingBill.setProductTypeNo(billDetails.getProductTypeNo());
        if (billDetails.getRateOfInterest() != null) existingBill.setRateOfInterest(billDetails.getRateOfInterest());
        if (billDetails.getAmount() != null) existingBill.setAmount(billDetails.getAmount());
        if (billDetails.getAmountInWords() != null) existingBill.setAmountInWords(billDetails.getAmountInWords());
        if (billDetails.getPresentValue() != null) existingBill.setPresentValue(billDetails.getPresentValue());
        if (billDetails.getGrams() != null) existingBill.setGrams(billDetails.getGrams());
        if (billDetails.getMonthlyIncome() != null) existingBill.setMonthlyIncome(billDetails.getMonthlyIncome());
        if (billDetails.getRedemptionDate() != null) existingBill.setRedemptionDate(billDetails.getRedemptionDate());
        if (billDetails.getRedemptionInterest() != null) existingBill.setRedemptionInterest(billDetails.getRedemptionInterest());
        if (billDetails.getRedemptionTotal() != null) existingBill.setRedemptionTotal(billDetails.getRedemptionTotal());
        if (billDetails.getRedemptionStatus() != null) existingBill.setRedemptionStatus(billDetails.getRedemptionStatus());
        if (billDetails.getBillRedemSerial() != null) existingBill.setBillRedemSerial(billDetails.getBillRedemSerial());
        if (billDetails.getBillRedemNo() != null) existingBill.setBillRedemNo(billDetails.getBillRedemNo());
        if (billDetails.getComments() != null) existingBill.setComments(billDetails.getComments());

        Bill updatedBill = billService.saveBill(existingBill);
        return ResponseEntity.ok(updatedBill);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable("id") Integer id) {
        Optional<Bill> billOptional = billService.findById(id);
        if (billOptional.isPresent()) {
            billService.deleteBill(id);
            return ResponseEntity.ok("Bill deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Bill not found");
        }
    }
}
