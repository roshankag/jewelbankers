package com.jewelbankers.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.jewelbankers.Utility.TimeFormatterUtil;
import com.jewelbankers.aop.SwitchDatabase;
import com.jewelbankers.entity.Bill;
import com.jewelbankers.services.BillService;
//import com.jewelbankers.services.FileStorageService;
import com.jewelbankers.services.SettingsService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/jewelbankersapi/bills")
@CrossOrigin(origins = "http://localhost:4200")
@SwitchDatabase
public class BillController {

    @Autowired
    private BillService billService;
    
    @Autowired
    private SettingsService settingsService; 

//    @GetMapping("/searchByProductTypeNo")
//    public ResponseEntity<List<Bill>> getBillsByProductTypeNo(@RequestParam Long productTypeNo) {
//        List<Bill> bills = billService.findBillsByProductTypeNo(productTypeNo);
//        return ResponseEntity.ok(bills);
//    }
//
//    @GetMapping("/searchByRedemptionStatus")
//    public ResponseEntity<List<Bill>> getBillsByRedemptionStatus(@RequestParam Character redemptionStatus) {
//        List<Bill> bills = billService.findBillsByRedemptionStatus(redemptionStatus);
//        return ResponseEntity.ok(bills);
//    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createBill(@RequestPart("bill") Bill bill, 
            @RequestPart(value = "photo", required = false) MultipartFile photo,
            @RequestPart(value = "articlephoto", required = false) MultipartFile articlephoto) {
    

        try {
            // Delegate the photo processing to the service layer
            Bill createdBill = billService.saveBill(bill, photo, articlephoto);

            // Create a response map to hold both the message and the bill
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Bill successfully pledged with customerId: " + createdBill.getCustomer()!= null && 
            		createdBill.getCustomer().getCustomerid() != null ? 
            				createdBill.getCustomer().getCustomerid() : "");
            
         // Add pledgeTime formatted response
           // String formattedPledgeTime = TimeFormatterUtil.formatTo12Hour(createdBill.getPledgeTime());
            //response.put("pledgeTime", formattedPledgeTime);            
            response.put("bill", createdBill);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping(value = "/{billSequence}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> updateBill(
            @PathVariable Long billSequence,
            @RequestPart("bill") Bill bill,  
            @RequestPart(value = "photo", required = false) MultipartFile photo,
            @RequestPart(value= "articlephoto", required = false) MultipartFile articlephoto)
    {
        try {
            // Validate the Bill object
            if (bill == null) {
                return ResponseEntity.badRequest().body(null);
            }

            // Call the service to update the bill
            Bill updatedBill = billService.updateBill(billSequence, bill, photo, articlephoto);

            // Create a response map to hold both the message and the updated bill
            Map<String, Object> response = new HashMap<>();
            
         // Adding pledge time to the response
           // String pledgeTimeFormatted = TimeFormatterUtil.formatTo12Hour(updatedBill.getPledgeTime());
            
            //response.put("pledgeTime", pledgeTimeFormatted);  
            response.put("message", "Bill updated successfully with customerId: " + updatedBill.getCustomer().getCustomerid());
            response.put("bill", updatedBill);

            return ResponseEntity.ok(response);
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


    @GetMapping("/export/excel")
    public ResponseEntity<?> exportBillsToExcel(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) Integer amount,
            @RequestParam(required = false) Character status,
            @RequestParam(required = false) Integer productTypeNo,
            @RequestParam(required = false) String sortOrder) throws IOException {

        // Call the service to handle the Excel export logic
        ByteArrayInputStream excelFile = billService.exportBillsToExcel(search, fromDate, endDate, amount, status, productTypeNo, sortOrder);

        if (excelFile == null) {
            // Return a 200 OK response with a message indicating no bills were found or an error occurred
            Map<String, String> response = new HashMap<>();
            response.put("message", "No bills found with the provided search criteria or an error occurred.");
            return ResponseEntity.ok(response);
        }

        // Set headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=bills.xlsx");

        
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(excelFile));
    }
    
//    @GetMapping("/export/excel")
//    public void exportBillsToExcel(
//            @RequestParam(required = false) String search,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
//            @RequestParam(required = false) Integer amount,
//            @RequestParam(required = false) Character status,
//            @RequestParam(required = false) Integer productTypeNo,
//            @RequestParam(required = false) String sortOrder,
//            HttpServletResponse response) throws IOException {
//
//        // Call the service to handle the Excel export logic
//        ByteArrayInputStream excelFile = billService.exportBillsToExcel(search, fromDate, endDate, amount, status, productTypeNo, sortOrder);
//
//        if (excelFile == null) {
//            // If no data is found, set a 204 (No Content) status
//            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//            response.getWriter().write("No bills found with the provided search criteria or an error occurred.");
//            return;
//        }
//
//        // Set the content type and disposition for the response
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-Disposition", "attachment; filename=bills.xlsx");
//
//        // Stream the file directly to the response output
//        try (OutputStream outputStream = response.getOutputStream();
//             InputStream inputStream = excelFile) {
//            byte[] buffer = new byte[1024]; // Chunk size (1 KB)
//            int bytesRead;
//
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//                outputStream.flush(); // Flush the stream to ensure chunks are sent immediately
//            }
//        } catch (IOException e) {
//            // Handle IOException if the client aborts or other errors occur
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("An error occurred while streaming the file.");
//        }
//    }
//



    @GetMapping("/number")
    public ResponseEntity<?> getBillsByBillNo(@RequestParam(value = "billNo", required = false) Integer billNo,
                                              @RequestParam(value = "billSequence", required = false) Long billSequence,
                                              @RequestParam(value = "billSerial", required = false) Character billSerial,
                                              @RequestParam(value = "redemptionDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate redemptionDate) {
        // Call the service method with the redemptionDate
        List<Bill> bills = billService.findBillsByBillNo(billSerial, billNo, billSequence, redemptionDate);
        if (bills.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No bills found", "No bills found with billNo: " + billNo));
        } else {
            return ResponseEntity.ok(bills);
        }
    }


    @GetMapping
    public ResponseEntity<Page<Bill>> getAllBills(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "100") int size) {
        Page<Bill> bills = billService.getAllBills(page, size);
        return ResponseEntity.ok(bills);
    }
   
    
    @GetMapping("/open-by-name")
    public ResponseEntity<?> getOpenBillsByCustomer(@RequestParam String customerName) {
        // Fetch open bills for the customer based on customerId
    	 List<Map<String, Object>> bills = billService.getOpenBillsForCustomer(customerName);

        if (bills.isEmpty()) {
            // Return a 200 OK response with a message indicating no open bills were found
            Map<String, String> response = new HashMap<>();
            response.put("message", "No open bills found for the specified customer.");
            return ResponseEntity.ok(response);
        }

        // Return the list of open bills with a 200 OK status
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
            @RequestParam(required = false) Integer productTypeNo,
            @RequestParam(required = false, defaultValue = "0") int page, // Default page 0
            @RequestParam(required = false, defaultValue = "100") int size) { // Limit to 100 records

        Pageable pageable = PageRequest.of(page, size); // Set page size to 100

        // Fetch results from service with pagination applied
        List<Bill> bills = billService.findBillsBySearch(search, fromDate, toDate, amount, status, productTypeNo, null);

        // If you want to return only the first 100 results:
        List<Bill> limitedBills = bills.stream().limit(100).collect(Collectors.toList());

        if (limitedBills.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No bills found with the provided search criteria.");
            return ResponseEntity.ok(response);
        }

        // Return the list of bills with pagination
        return ResponseEntity.ok(limitedBills);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBillByBillNo(@PathVariable("id") Long id, @RequestBody Bill billDetails) {
        try {
            // Call the service to update the bill
            Bill updatedBill = billService.updateBill(id, billDetails);

            // Create the response map with redeem-specific information
            Map<String, Object> response = new HashMap<>();

            // Adding redeem time to the response if available
            String redeemTimeFormatted = TimeFormatterUtil.formatTo12Hour(updatedBill.getRedeemTime());
            
            // Adding the redeem-related message
            response.put("message", "Bill updated successfully with redemption details for Bill No: " + updatedBill.getBillNo());
            response.put("bill", updatedBill);
            response.put("redeemTime", redeemTimeFormatted);  // Adding formatted redeem time to the response

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            // Handle case where the bill is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Bill not found", ex.getMessage()));
        } catch (Exception ex) {
            // Handle other exceptions
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
    
//    @GetMapping("customerpdf/{billSequence}")
//    public ResponseEntity<?> generateAndSendBill(@PathVariable Long billSequence) {
//        try {
//        	
//        	// Fetch settings from a database or service
//        	Map<String, String> settingsMap = settingsService.getShopDetails();
//        	
//        	
//        	Optional<Bill> bill = billService.findById(billSequence);
//        	String filename="Bill-"+bill.get().getBillSerial()+""+bill.get().getBillNo();
//            // Generate PDF for the pledge bill
//            ByteArrayInputStream pdfStream = billService.generateCustomerSendBill(bill.get(),settingsMap);
//
//            // Set headers for PDF response
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+filename+".pdf");
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(new InputStreamResource(pdfStream));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
//        }
//    }
    
    @GetMapping("sendbill/{billSequence}")
    public ResponseEntity<?> generateSendBill(@PathVariable Long billSequence) {
        try {
            // Fetch settings from your settings service
            Map<String, String> settingsMap = settingsService.getShopDetails();

            // Fetch the bill using the billService
            Optional<Bill> bill = billService.findById(billSequence);
            if (bill.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill not found");
            }

            String filename = "Bill-" + bill.get().getBillSerial() + "" + bill.get().getBillNo();

            // Generate PDF using the generateSendBill method
            ByteArrayInputStream pdfStream = billService.generateSendBill(bill.get(), settingsMap);

            // Set headers for the PDF response
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename + ".pdf");

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
    
    @GetMapping("/product-descriptions")
    public ResponseEntity<List<String>> getProductDescriptionsByPrefix(@RequestParam("prefix") String prefix) {
        List<String> filteredDescriptions = billService.getAllProductDescriptions(prefix);
        return ResponseEntity.ok(filteredDescriptions);
    }
    
}
