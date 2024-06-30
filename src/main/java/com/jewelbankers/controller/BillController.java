package com.jewelbankers.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.entity.Bill;
import com.jewelbankers.services.BillService;

@RestController
@RequestMapping("/bills")
public class BillController {
   
    @Autowired
    private BillService billService;

    @GetMapping("/number")
    public ResponseEntity<?> getBillsByBillNo(@RequestParam(value = "billNo",required  = false) Integer billNo,
    		@RequestParam(value = "billSequence",required  = false) Long billSequence,
    @RequestParam(value = "billSerial",required  = false) Character billSerial) {
        List<Bill> bills = billService.findBillsByBillNo(billSerial,billNo,billSequence);
        if (bills.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No bills found with billSequence: " + billNo);
        } else {
            return ResponseEntity.ok(bills);
        }
    }

    @GetMapping
    public Page<Bill> getAllBills(@RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size) {
        return billService.getAllBills(page , size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBillById(@PathVariable("id") Long id) {
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
    
    @GetMapping("/search")
    public List<Bill> getBillsByCustomerName(@RequestParam(value = "customerName", required = false) String customerName, 
    		@RequestParam(value = "street", required = false)  String street , 
    		@RequestParam(value = "billNo", required = false)  Integer billNo) {
        return billService.findBillsByCustomerName(customerName,street,billNo);
    }
    
	/*
	 * @GetMapping("/search") public List<Bill>
	 * getBillsByCustomerStreet(@RequestParam("street") String street) { return
	 * billService.findBillsByCustomerStreet(street); }
	 */

    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBillByBillNo(@PathVariable("id") Long id, @RequestBody Bill billDetails) {
        Optional<Bill> billOptional = billService.findById(id);
        if (!billOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Bill existingBill = billOptional.get();
        
        // Update fields individually
        if (billDetails.getBillSerial() != null) existingBill.setBillSerial(billDetails.getBillSerial());
        if (billDetails.getBillNo() != null) existingBill.setBillNo(billDetails.getBillNo());
        if (billDetails.getBillDate() != null) existingBill.setBillDate(billDetails.getBillDate());
        if (billDetails.getCustomer() != null) existingBill.setCustomer(billDetails.getCustomer());
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
    public ResponseEntity<String> deleteBill(@PathVariable("id") Long id) {
        Optional<Bill> billOptional = billService.findById(id);
        if (billOptional.isPresent()) {
            billService.deleteBill(id);
            return ResponseEntity.ok("Bill deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Bill not found");
        }
    }
}
