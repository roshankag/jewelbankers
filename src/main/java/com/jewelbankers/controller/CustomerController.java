package com.jewelbankers.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jewelbankers.entity.Customer;
import com.jewelbankers.services.CustomerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers(
            @RequestParam(required = false) String customerName, 
            @RequestParam(required = false) Long phoneno) {
        
    	// Check if either customerName or phoneNo is provided
        if (customerName != null && !customerName.isEmpty() && phoneno != null) {
            // If both customerName and phoneNo are provided, filter by both
            return customerService.findByNameAndPhone(customerName, phoneno);
        } 
        
        else if (customerName != null && !customerName.isEmpty()) {
            // If only customerName is provided, filter by customerName
            return customerService.findByNameStartingWith(customerName);
        } 
        
        else if (phoneno != null) {
            // If only phoneNo is provided, filter by phoneNo
            return customerService.findByPhoneNo(phoneno);
        } 
        
        else {
            // If neither is provided, return all customers
            return customerService.findAll();
        }
    }
    
//    @PostMapping("/add")
//    public Customer addCustomer(@RequestBody Customer customerRequest) {
//        System.out.println(customerRequest.toString());
//
//        // Convert CustomerRequest to Customer entity
//        Customer customer = new Customer();
//        customer.setCustomerName(customerRequest.getCustomerName());
//        customer.setAddress(customerRequest.getAddress());
//        customer.setPhoneno(customerRequest.getPhoneno());
//        customer.setPhoto(customerRequest.getPhoto());
//
//        return customerService.addCustomer(customer);
//    }
    
//    @PostMapping("/add")
//    public Customer addCustomer(
//        @RequestParam("customerName") String customerName,
//        @RequestParam("address") String address,
//        @RequestParam("phoneno") Long phoneno,
//        @RequestParam(value = "photo", required = false) MultipartFile photo,
//        @RequestParam Map<String, String> settingsMap) {
//
//        Customer customer = new Customer();
//        customer.setCustomerName(customerName);
//        customer.setAddress(address);
//        customer.setPhoneno(phoneno);
//
//        // Handle the photo file if provided
//        if (photo != null && !photo.isEmpty()) {
//            try {
//                // Save the photo with customer name as filename
//                String photoPath = customerService.savePhoto(photo, customerName, settingsMap);
//                customer.setPhoto(photoPath);  // Set the photo path in the Customer entity
//            } catch (IOException e) {
//                throw new RuntimeException("Photo upload failed", e);
//            }
//        }
//
//        return customerService.addCustomer(customer);
//    }
    
//    @GetMapping("/photos/{photo}")
//    public ResponseEntity<byte[]> getCustomerPhoto(@PathVariable String photo) {
//        try {
//            // Retrieve the photo file based on the photoName
//            Path photoPath = Paths.get("C:\\Users\\Roshan B T\\backend\\null\\uploads", photo); // Adjust the path
//            byte[] imageBytes = Files.readAllBytes(photoPath);
//
//            // Set the response headers to indicate the content type and length
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG); // Change if your image format is different
//            headers.setContentLength(imageBytes.length);
//            
//            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }



    
    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteCustomer(@PathVariable("id") Long id) {
        return customerService.deleteCustomer(id);
        //return "Sucessfully deleted for Customer Id "+id;
        
    }
    
//    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ResponseBody
//    public Customer updateCustomer(
//        @PathVariable("id") Long id,
//        @RequestParam("customerName") String customerName,
//        @RequestParam("address") String address,
//        @RequestParam("phoneno") Long phoneno,
//        @RequestParam(value = "photo", required = false) MultipartFile photo) {
//
//        // Retrieve the existing customer using findById
//        Customer existingCustomer = customerService.findById(id)
//            .orElseThrow(() -> new RuntimeException("Customer not found"));
//
//        // Update customer fields
//        existingCustomer.setCustomerName(customerName);
//        existingCustomer.setAddress(address);
//        existingCustomer.setPhoneno(phoneno);
//
//        // Handle the photo update if provided
//        if (photo != null && !photo.isEmpty()) {
//            try {
//                // Save the new photo and update the customer entity
//                String photoPath = customerService.savePhoto(photo, customerName, null); // Assuming settingsMap is not needed here
//                existingCustomer.setPhoto(photoPath);
//            } catch (IOException e) {
//                throw new RuntimeException("Photo upload failed", e);
//            }
//        }
//
//        // Save the updated customer
//        return customerService.updateCustomer(id, existingCustomer);
//    }
    
    @PutMapping("/{id}")
    @ResponseBody
    public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    
    @GetMapping("/search")
    @ResponseBody
    public List<Customer> searchCustomersByName(@RequestParam("customerName") String customerName) {
        return customerService.findCustomersByName(customerName);
    }

//(@PathVariable("id") Long id)
    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable("id") Long id) {
        return customerService.findById(id);
        /*return customer.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());*/
    }
    
}
