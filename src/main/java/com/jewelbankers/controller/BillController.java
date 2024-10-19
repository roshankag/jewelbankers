package com.jewelbankers.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jewelbankers.Utility.ErrorResponse;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.entity.Customer;
import com.jewelbankers.excel.ExcelGenerator;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Bill> createBill( @RequestPart("bill") Bill bill, 
            @RequestPart(value = "photo", required = false) MultipartFile photo ) {
    	
        try {
            // Delegate the photo processing to the service layer
            Bill createdBill = billService.saveBill(bill, photo);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBill);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping(value = "/{billSequence}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Bill> updateBill(
            @PathVariable Long billSequence,
            @RequestPart("bill") Bill bill,  // Bind the Bill object from the request part
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        try {
            // Validate the Bill object (Optional)
            if (bill == null) {
                return ResponseEntity.badRequest().body(null);
            }

            // Call the service to update the bill
            Bill updatedBill = billService.updateBill(billSequence, bill, photo); // No photo if not provided
            return ResponseEntity.ok(updatedBill);
        } catch (IOException e) {
            // Log the exception (optional)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            // Handle other exceptions that might occur
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    
    
//    @PostMapping("/create")
//    public ResponseEntity<?> createBill(@RequestBody Bill bill) {
//        try {
//            // Save the bill
//            Bill createdBill = billService.saveBill(bill);
//
//            // Fetch shop details from settings
//            Map<String, String> shopDetails = billService.getShopDetailsForBill();
//
//             catch// Generate PDF for the pledge bill (pass the entire shopDetails map)
//            ByteArrayInputStream bis = BillPdfGenerator.generatePledgeBill(createdBill, shopDetails);
//
//            // Set headers for PDF response
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", "inline; filename=pledge_bill.pdf");
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(new InputStreamResource(bis));
//        } (Exception e) {
//            // Handle any exceptions (logging, custom error response, etc.)
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred while generating the bill.");
//        }
//    }

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

    
//    @GetMapping("/export/excel")
//    public ResponseEntity<?> exportBillsToExcel() throws IOException {
//        ByteArrayInputStream in = billService.exportBillsToExcel();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment; filename=bills.xlsx");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(new InputStreamResource(in));
//    }
    
    @GetMapping("/export/excel")
    public ResponseEntity<?> exportBillsToExcel(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) Integer amount,
            @RequestParam(required = false) Character status,
            @RequestParam(required = false) Integer productTypeNo,
            @RequestParam(required = false) String sortOrder) throws IOException {

        // Retrieve the list of bills based on the search criteria
        List<Bill> bills = billService.findBillsBySearch(search, fromDate, endDate, amount, status, productTypeNo, sortOrder);
        
        if (bills.isEmpty()) {
            // Return a 200 OK response with a message indicating no bills were found
            Map<String, String> response = new HashMap<>();
            response.put("message", "No bills found with the provided search criteria.");
            return ResponseEntity.ok(response); // Returning a Map<String, String> when no bills are found
        }

        // Generate the Excel file from the list of bills
        ByteArrayInputStream excelFile = ExcelGenerator.generateBillExcel(bills);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=bills.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) // Use this MIME type for .xlsx files
                .body(new InputStreamResource(excelFile));
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
    public ResponseEntity<?> getBillsBySearch(@RequestParam(value = "search", required = false) String search) {
        List<Bill> bills = billService.findBillsBySearch(search);
        
        if (bills.isEmpty()) {
            // **Return a 200 OK response with a message indicating no bills were found**
            Map<String, String> response = new HashMap<>();
            response.put("message", "No bills found with the provided search criteria.");
            return ResponseEntity.ok(response); // Returning a Map<String, String> when no bills are found
        }
        
        // **Return the list of bills with a 200 OK status**
        return ResponseEntity.ok(bills); // Returning the list of bills
    }
    
    @GetMapping("/fullsearch")
    public ResponseEntity<?> searchBills(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            @RequestParam(required = false) Integer amount,
            @RequestParam(required = false) Character status,
            @RequestParam(required = false) Integer productTypeNo) {
        
        List<Bill> bills = billService.findBillsBySearch(search, fromDate, toDate, amount, status, productTypeNo, null);
        if (bills.isEmpty()) {
            // **Return a 200 OK response with a message indicating no bills were found**
            Map<String, String> response = new HashMap<>();
            response.put("message", "No bills found with the provided search criteria.");
            return ResponseEntity.ok(response); // **Highlighted Change**
        }
        
        // **Return the list of bills with a 200 OK status**
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
    
    @GetMapping("customerpdf/{billSequence}")
    public ResponseEntity<?> generateAndSendBill(@PathVariable Long billSequence) {
        try {
        	
        	// Fetch settings from a database or service
        	Map<String, String> settingsMap = settingsService.getShopDetails();
        	
        	
        	Optional<Bill> bill = billService.findById(billSequence);
        	String filename="Bill-"+bill.get().getBillSerial()+""+bill.get().getBillNo();
            // Generate PDF for the pledge bill
            ByteArrayInputStream pdfStream = billService.generateCustomerSendBill(bill.get(),settingsMap);

            // Set headers for PDF response
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+filename+".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdfStream));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
        }
    }
    
    @GetMapping("officepdf/{billSequence}")
    public ResponseEntity<?> generateOfficeSendBill(@PathVariable Long billSequence) {
        try {
        	
        	// Fetch settings from a database or service
        	Map<String, String> settingsMap = settingsService.getShopDetails();
        	
        	
        	Optional<Bill> bill = billService.findById(billSequence);
        	String filename="Bill-"+bill.get().getBillSerial()+""+bill.get().getBillNo();
            // Generate PDF for the pledge bill
            ByteArrayInputStream pdfStream = billService.generateOfficeSendBill(bill.get(),settingsMap);

            // Set headers for PDF response
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+filename+".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdfStream));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
        }
    }
    
    
//    @GetMapping("pdf/{billSequence}")
//    public ResponseEntity<?> generateAndSendBill(@PathVariable Long billSequence) {
//        try {
//            // Fetch settings from a database or service
//            Map<String, String> settingsMap = settingsService.getShopDetails();
//            
//            Optional<Bill> bill = billService.findById(billSequence);
//            
//            // Generate PDFs for the bill
//            ByteArrayInputStream[] pdfStreams = billService.generateAndSendBill(bill.get(), settingsMap);
//            
//            // Set filenames
//            String customerFilename = "Bill-" + bill.get().getBillSerial() + "-" + bill.get().getBillNo() + "-customer.pdf";
//            String officeFilename = "Bill-" + bill.get().getBillSerial() + "-" + bill.get().getBillNo() + "-office.pdf";
//            
//            // Create headers for the response
//            HttpHeaders headers = new HttpHeaders();
//            
//            // Create an array of InputStreamResource to hold the PDF streams
//            InputStreamResource customerResource = new InputStreamResource(pdfStreams[0]);
//            InputStreamResource officeResource = new InputStreamResource(pdfStreams[1]);
//
//            // Build a response entity
//            Map<String, Object> response = new HashMap<>();
//            response.put("customerPdf", customerResource);
//            response.put("officePdf", officeResource);
//            
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(response);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error generating PDFs: " + e.getMessage());
//        }
//    }
//
//    
    
//    @GetMapping("pdf/{billSequence}")
//    public ResponseEntity<?> generateAndSendBill(@PathVariable Long billSequence) {
//        try {
//            // Fetch settings from a database or service
//            Map<String, String> settingsMap = settingsService.getShopDetails();
//
//            Optional<Bill> bill = billService.findById(billSequence);
//
//            if (bill.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("Bill not found with sequence: " + billSequence);
//            }
//
//            // Generate PDFs for the bill
//            ByteArrayInputStream[] pdfStreams = billService.generateAndSendBill(bill.get(), settingsMap);
//
//            // Set filenames (optional if you don't need them in the response)
//            String customerFilename = "Bill-" + bill.get().getBillSerial() + "-" + bill.get().getBillNo() + "-customer.pdf";
//            String officeFilename = "Bill-" + bill.get().getBillSerial() + "-" + bill.get().getBillNo() + "-office.pdf";
//
//            // Read the InputStream into byte arrays
//            byte[] customerPdfBytes = pdfStreams[0].readAllBytes();
//            byte[] officePdfBytes = pdfStreams[1].readAllBytes();
//
//            // Optionally, encode to Base64
//            String customerBase64Pdf = Base64.getEncoder().encodeToString(customerPdfBytes);
//            String officeBase64Pdf = Base64.getEncoder().encodeToString(officePdfBytes);
//
//            // Create headers for the response
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", "attachment; filename=" + customerFilename); // Change for office PDF if needed
//
//            // Build a response entity
//            Map<String, Object> response = new HashMap<>();
//            response.put("customerPdf", customerBase64Pdf);
//            response.put("officePdf", officeBase64Pdf);
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(response);
//
//        } 
//        catch (Exception e) 
//        {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error generating PDFs: " + e.getMessage());
//        }
//    }
    @GetMapping("redeempdf/{billSequence}")
    public ResponseEntity<?> generateAndRedeemBillPdf(@PathVariable Long billSequence) {
        try {
        	
        	// Fetch settings from a database or service
        	Map<String, String> settingsMap = settingsService.getShopDetails();
        	
        	
        	Optional<Bill> bill = billService.findById(billSequence);
        	String filename="Bill-"+bill.get().getBillRedemSerial()+""+bill.get().getBillRedemNo();
            // Generate PDF for the redeem bill
            ByteArrayInputStream pdfStream = billService.generateAndRedeemBillPdf(bill.get(),settingsMap);

            // Set headers for PDF response
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+filename+".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdfStream));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
        }
    }
    
}


