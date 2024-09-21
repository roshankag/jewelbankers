package com.jewelbankers.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.Utility.BillPdfGenerator;
import com.jewelbankers.Utility.ErrorResponse;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.exception.ResourceNotFoundException;
import com.jewelbankers.services.BillService;
//import com.jewelbankers.services.FileStorageService;
import com.jewelbankers.services.SettingsService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/bills")
@CrossOrigin(origins = "http://localhost:4200")
public class BillController {

    @Autowired
    private BillService billService;
    
    @Autowired
    private SettingsService settingsService; 

    @GetMapping("/searchByProductTypeNo")
    public ResponseEntity<List<Bill>> getBillsByProductTypeNo(@RequestParam Long productTypeNo) {
        List<Bill> bills = billService.findBillsByProductTypeNo(productTypeNo);
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/searchByRedemptionStatus")
    public ResponseEntity<List<Bill>> getBillsByRedemptionStatus(@RequestParam Character redemptionStatus) {
        List<Bill> bills = billService.findBillsByRedemptionStatus(redemptionStatus);
        return ResponseEntity.ok(bills);
    }

//    @PostMapping("/create")
//    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
//    	System.out.println();
//        Bill createdBill = billService.saveBill(bill);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdBill);
//    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createBill(@RequestBody Bill bill) {
        try {
            // Save the bill
            Bill createdBill = billService.saveBill(bill);

            // Fetch shop details from settings
            Map<String, String> shopDetails = billService.getShopDetailsForBill();

            // Generate PDF for the pledge bill (pass the entire shopDetails map)
            ByteArrayInputStream bis = BillPdfGenerator.generatePledgeBill(createdBill, shopDetails);

            // Set headers for PDF response
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=pledge_bill.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } catch (Exception e) {
            // Handle any exceptions (logging, custom error response, etc.)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while generating the bill.");
        }
    }

//    @PostMapping("/create")
//    public ResponseEntity<Bill> createBillWithPhoto(
//            @RequestParam("bill") String billJson,
//            @RequestParam("photo") MultipartFile photo) {
//        
//        // Convert JSON string to Bill object
//        ObjectMapper objectMapper = new ObjectMapper();
//        Bill bill;
//        try {
//            bill = objectMapper.readValue(billJson, Bill.class);
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//        
//        // Save the bill with the photo
//        Bill createdBill;
//        try {
//            createdBill = billService.saveBill(bill, photo);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body(null);
//        }
//        
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdBill);
//    }
    
	/*
	 * @PostMapping("/create") public ResponseEntity<?> createBillWithPhoto(
	 * 
	 * @RequestParam("bill") String billJson,
	 * 
	 * @RequestParam("photo") MultipartFile photo) {
	 * 
	 * // Convert JSON string to Bill object ObjectMapper objectMapper = new
	 * ObjectMapper(); objectMapper.registerModule(new JavaTimeModule());
	 * objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	 * 
	 * Bill bill; try {
	 * 
	 * bill = objectMapper.readValue(billJson, Bill.class); } catch (IOException e)
	 * { String errorMessage = "Failed to parse the bill JSON: " + e.getMessage();
	 * return ResponseEntity.badRequest().body(errorMessage); }
	 * 
	 * // Save the bill with the photo Bill createdBill; try { createdBill =
	 * billService.saveBill(bill, photo); } catch (RuntimeException e) {
	 * e.printStackTrace(); return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	 * .body("Error saving bill: " + e.getMessage()); }
	 * 
	 * return ResponseEntity.status(HttpStatus.CREATED).body(createdBill); }
	 */

    
    @GetMapping("/export/excel")
    public ResponseEntity<?> exportBillsToExcel() throws IOException {
        ByteArrayInputStream in = billService.exportBillsToExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=bills.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(in));
    }

    @GetMapping("/number")
    public ResponseEntity<?> getBillsByBillNo(@RequestParam(value = "billNo", required = false) Integer billNo,
                                               @RequestParam(value = "billSequence", required = false) Long billSequence,
                                               @RequestParam(value = "billSerial", required = false) Character billSerial) {
        List<Bill> bills = billService.findBillsByBillNo(billSerial, billNo, billSequence);
        if (bills.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No bills found", "No bills found with billSequence: " + billNo));
        } else {
            return ResponseEntity.ok(bills);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Bill>> getAllBills(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Bill> bills = billService.getAllBills(page, size);
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBillById(@PathVariable("id") Long id) {
        Optional<Bill> billOptional = billService.findById(id);
        
        if (billOptional.isPresent()) {
            return ResponseEntity.ok(billOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Bill not found", "Bill with id " + id + " not found"));
        }
    }

    @GetMapping("/next-bill-number")
    public ResponseEntity<Map<String, Integer>> getBillNumbers() {
        int nextBillNo = billService.getNextBillNo();
        Map<String, Integer> response = new HashMap<>();
        response.put("nextBillNo", nextBillNo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/next-redeem-number")
    public ResponseEntity<Map<String, Integer>> getRedeemNumbers() {
        int nextBillRedemNo = billService.getNextBillRedemNo();
        Map<String, Integer> response = new HashMap<>();
        response.put("nextBillRedemNo", nextBillRedemNo);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Bill>> getBillsBySearch(@RequestParam(value = "search", required = false) String search) {
        List<Bill> bills = billService.findBillsBySearch(search);
        return ResponseEntity.ok(bills);
    }
    
    @GetMapping("/fullsearch")
    public ResponseEntity<List<Bill>> searchBills(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            @RequestParam(required = false) Integer amount,
            @RequestParam(required = false) Character status,
            @RequestParam(required = false) Integer productTypeNo) {
        
        List<Bill> bills = billService.findBillsBySearch(search, fromDate, toDate, amount, status, productTypeNo);
        if (bills.isEmpty()) {
            throw new ResourceNotFoundException("No bills found with the provided search criteria.");
        }
        return ResponseEntity.ok(bills);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBillByBillNo(@PathVariable("id") Long id, @RequestBody Bill billDetails) {
        try {
            Bill updatedBill = billService.updateBill(id, billDetails);
            return ResponseEntity.ok(updatedBill);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Bill not found", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error updating bill", ex.getMessage()));
        }
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateBillByBillNo(@PathVariable("id") Long id, @RequestBody Bill billDetails) {
//        Optional<Bill> billOptional = billService.findById(id);
//        if (!billOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorResponse("Bill not found", "Bill with id " + id + " not found"));
//        }
//
//        Bill existingBill = billOptional.get();
//        
//        if (billDetails.getBillSerial() != null) existingBill.setBillSerial(billDetails.getBillSerial());
//      if (billDetails.getBillNo() != null) existingBill.setBillNo(billDetails.getBillNo());
//      if (billDetails.getBillDate() != null) existingBill.setBillDate(billDetails.getBillDate());
//      if (billDetails.getCustomer() != null) existingBill.setCustomer(billDetails.getCustomer());
//      if (billDetails.getCareOf() != null) existingBill.setCareOf(billDetails.getCareOf());
//      if (billDetails.getProductTypeNo() != null) existingBill.setProductTypeNo(billDetails.getProductTypeNo());
//      if (billDetails.getRateOfInterest() != null) existingBill.setRateOfInterest(billDetails.getRateOfInterest());
//      if (billDetails.getAmount() != null) existingBill.setAmount(billDetails.getAmount());
//      if (billDetails.getAmountInWords() != null) existingBill.setAmountInWords(billDetails.getAmountInWords());
//      if (billDetails.getPresentValue() != null) existingBill.setPresentValue(billDetails.getPresentValue());
//      if (billDetails.getGrams() != null) existingBill.setGrams(billDetails.getGrams());
//      if (billDetails.getMonthlyIncome() != null) existingBill.setMonthlyIncome(billDetails.getMonthlyIncome());
//      if (billDetails.getRedemptionDate() != null) existingBill.setRedemptionDate(billDetails.getRedemptionDate());
//      if (billDetails.getRedemptionInterest() != null) existingBill.setRedemptionInterest(billDetails.getRedemptionInterest());
//      if (billDetails.getRedemptionTotal() != null) existingBill.setRedemptionTotal(billDetails.getRedemptionTotal());
//      if (billDetails.getRedemptionStatus() != null) existingBill.setRedemptionStatus(billDetails.getRedemptionStatus());
//      if (billDetails.getBillRedemSerial() != null) existingBill.setBillRedemSerial(billDetails.getBillRedemSerial());
//      if (billDetails.getBillRedemNo() != null) existingBill.setBillRedemNo(billDetails.getBillRedemNo());
//      if (billDetails.getComments() != null) existingBill.setComments(billDetails.getComments());
//
//        Bill updatedBill = billService.saveBill(existingBill);
//        return ResponseEntity.ok(updatedBill);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable("id") Long id) {
        Optional<Bill> billOptional = billService.findById(id);
        if (billOptional.isPresent()) {
            billService.deleteBill(id);
            return ResponseEntity.ok("Bill deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Bill not found", "Bill with id " + id + " not found"));
        }
    }
    
    @DeleteMapping("/redeem")
    public ResponseEntity<String> deleteRedeemBill(@RequestParam Character billSerial, @RequestParam Integer billNo) {
        boolean isDeleted = billService.deleteRedeemBill(billSerial, billNo);
        
        if (isDeleted) {
            return ResponseEntity.ok("Redeem bill deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Redeem bill not found");
        }
    }
}


//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.jewelbankers.Utility.ErrorResponse;
//import com.jewelbankers.entity.Bill;
//import com.jewelbankers.repository.BillRepository;
//import com.jewelbankers.services.BillService;
//import com.jewelbankers.services.FileStorageService;
//
//@RestController
//@RequestMapping("/bills")
//@CrossOrigin(origins = "http://localhost:4200")
//public class BillController {
//   
//    @Autowired
//    private BillService billService;
//    
////    @Autowired
////    private BillRepository billRepository;
////    
////    @Autowired
////    private FileStorageService fileStorageService;
//    
////  http://localhost:8080/jewelbankersapi/bills/searchByProductTypeNo?productTypeNo=5
//    @GetMapping("/searchByProductTypeNo")
//    public ResponseEntity<List<Bill>> getBillsByProductTypeNo(@RequestParam Long productTypeNo) {
//        List<Bill> bills = billService.findBillsByProductTypeNo(productTypeNo);
//        return ResponseEntity.ok(bills);
//    }
//    
////  http://localhost:8080/jewelbankersapi/bills/searchByRedemptionStatus?redemptionStatus=R
//    @GetMapping("/searchByRedemptionStatus")
//    public ResponseEntity<List<Bill>> getBillsByRedemptionStatus(@RequestParam Character redemptionStatus) {
//        List<Bill> bills = billService.findBillsByRedemptionStatus(redemptionStatus);
//        return ResponseEntity.ok(bills);
//    }
//    
////  http://localhost:8080/jewelbankers/bills/date?startDate=2024-01-01&endDate=2024-01-31    
//    @GetMapping("/date")
//    public ResponseEntity<List<Bill>> searchBills(@RequestParam String startDate, @RequestParam String endDate) {
//        List<Bill> bills = billService.searchBillsByDateRange(startDate, endDate);
//        return ResponseEntity.ok(bills);
//    }
//    
//    @PostMapping
//    public Bill createBill(@RequestBody Bill bill) {
//        return billService.saveBill(bill);
//    }
//    
//    @GetMapping("/export/excel")
//    public ResponseEntity<?> exportBillsToExcel() throws IOException {
//    	try {
//    		ByteArrayInputStream in = billService.exportBillsToExcel();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", "attachment; filename=bills.xlsx");
//
//            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(new InputStreamResource(in));
//    	}catch (Exception e) {
//    		// Return an appropriate error response
//    		 ErrorResponse errorResponse = new ErrorResponse(
//    		            "Error occurred while exporting bills to Excel.",
//    		            e.getMessage()  // Include the exception message or other details as needed
//    		        );
//    		 e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Error occurred while exporting bills to Excel. Please try again later.");
//		}
//        
//    }
//    
////    @Autowired
////    private PdfService pdfService;
////
////    @GetMapping("/generatePledgeBillPdf")
////    public ResponseEntity<byte[]> generatePledgeBillPdf(
////    		@RequestParam(value = "billSequence",required  = false) Long billSequence)  throws DocumentException {
////    	Optional<Bill> bill = billService.findById(billSequence);
////        byte[] pdfBytes = pdfService.generatePledgeBillPdf(bill);
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_PDF);
////        headers.setContentDispositionFormData("filename", "pledgeBill.pdf");
////        return ResponseEntity.ok().headers(headers).body(pdfBytes);
////    }
////
////    @GetMapping("/generateRedeemBillPdf")
////    public ResponseEntity<byte[]> generateRedeemBillPdf(@RequestParam String customerName, @RequestParam String billDetails) throws DocumentException {
////        byte[] pdfBytes = pdfService.generateRedeemBillPdf(customerName, billDetails);
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_PDF);
////        headers.setContentDispositionFormData("filename", "redeemBill.pdf");
////        return ResponseEntity.ok().headers(headers).body(pdfBytes);
////    }
////    
//
//    @GetMapping("/number")
//    public ResponseEntity<?> getBillsByBillNo(@RequestParam(value = "billNo",required  = false) Integer billNo,
//    		@RequestParam(value = "billSequence",required  = false) Long billSequence,
//    @RequestParam(value = "billSerial",required  = false) Character billSerial) {
//    	//Log.debug("input bill no"+billNo+ "bill serial"+billSerial);
//        List<Bill> bills = billService.findBillsByBillNo(billSerial,billNo,billSequence);
//        if (bills.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                 .body("No bills found with billSequence: " + billNo);
//        } else {
//            return ResponseEntity.ok(bills);
//        }
//    }
//
//    @GetMapping
//    public Page<Bill> getAllBills(@RequestParam(value = "page",defaultValue = "0") int page,
//            @RequestParam(value = "size",defaultValue = "10") int size) {
//        return billService.getAllBills(page , size);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getBillById(@PathVariable("id") Long id) {
//        Optional<Bill> billOptional = billService.findById(id);
//        if (billOptional.isPresent()) {
//            return ResponseEntity.ok(billOptional.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill with id " + id + " not found");
//        }
//    }
//    
//    @GetMapping("/next-bill-number")
//    public ResponseEntity<Map<String, Integer>> getBillNumbers() {
//        int nextBillNo = billService.getNextBillNo();
//        Map<String, Integer> response = new HashMap<>();
//        response.put("nextBillNo", nextBillNo);
//        return ResponseEntity.ok(response);
//    }
//    @GetMapping("/next-redeem-number")
//    public ResponseEntity<Map<String, Integer>> getRedeemNumbers() {
//    	 int nextBillRedemNo = billService.getNextBillRedemNo();
//    	 Map<String, Integer> response = new HashMap<>();
//    	 response.put("nextBillRedemNo", nextBillRedemNo);
//    	 return ResponseEntity.ok(response);
//    }
////    
//    
//    
//     @GetMapping("/search")
//     public List<Bill> getBillsBySearch(@RequestParam(value = "search", required = false) String search)
//  		    {
//      return billService.findBillsBySearch(search);
//      }
//    
//	/*
//	 * @GetMapping("/search") public List<Bill>
//	 * getBillsByCustomerStreet(@RequestParam("street") String street) { return
//	 * billService.findBillsByCustomerStreet(street); }
//	 */
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Bill> updateBillByBillNo(@PathVariable("id") Long id, @RequestBody Bill billDetails) {
//        Optional<Bill> billOptional = billService.findById(id);
//        if (!billOptional.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Bill existingBill = billOptional.get();
//        
//        // Update fields individually
//        if (billDetails.getBillSerial() != null) existingBill.setBillSerial(billDetails.getBillSerial());
//        if (billDetails.getBillNo() != null) existingBill.setBillNo(billDetails.getBillNo());
//        if (billDetails.getBillDate() != null) existingBill.setBillDate(billDetails.getBillDate());
//        if (billDetails.getCustomer() != null) existingBill.setCustomer(billDetails.getCustomer());
//        if (billDetails.getCareOf() != null) existingBill.setCareOf(billDetails.getCareOf());
//        if (billDetails.getProductTypeNo() != null) existingBill.setProductTypeNo(billDetails.getProductTypeNo());
//        if (billDetails.getRateOfInterest() != null) existingBill.setRateOfInterest(billDetails.getRateOfInterest());
//        if (billDetails.getAmount() != null) existingBill.setAmount(billDetails.getAmount());
//        if (billDetails.getAmountInWords() != null) existingBill.setAmountInWords(billDetails.getAmountInWords());
//        if (billDetails.getPresentValue() != null) existingBill.setPresentValue(billDetails.getPresentValue());
//        if (billDetails.getGrams() != null) existingBill.setGrams(billDetails.getGrams());
//        if (billDetails.getMonthlyIncome() != null) existingBill.setMonthlyIncome(billDetails.getMonthlyIncome());
//        if (billDetails.getRedemptionDate() != null) existingBill.setRedemptionDate(billDetails.getRedemptionDate());
//        if (billDetails.getRedemptionInterest() != null) existingBill.setRedemptionInterest(billDetails.getRedemptionInterest());
//        if (billDetails.getRedemptionTotal() != null) existingBill.setRedemptionTotal(billDetails.getRedemptionTotal());
//        if (billDetails.getRedemptionStatus() != null) existingBill.setRedemptionStatus(billDetails.getRedemptionStatus());
//        if (billDetails.getBillRedemSerial() != null) existingBill.setBillRedemSerial(billDetails.getBillRedemSerial());
//        if (billDetails.getBillRedemNo() != null) existingBill.setBillRedemNo(billDetails.getBillRedemNo());
//        if (billDetails.getComments() != null) existingBill.setComments(billDetails.getComments());
//
//        Bill updatedBill = billService.saveBill(existingBill);
//        return ResponseEntity.ok(updatedBill);
//    }
//    
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteBill(@PathVariable("id") Long id) {
//        Optional<Bill> billOptional = billService.findById(id);
//        if (billOptional.isPresent()) {
//            billService.deleteBill(id);
//            return ResponseEntity.ok("Bill deleted successfully");
//        } else {
//            return ResponseEntity.status(404).body("Bill not found");
//        }
//    }
//}
