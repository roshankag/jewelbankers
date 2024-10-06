package com.jewelbankers.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @PostMapping("/add")
    public Customer addCustomer(@RequestBody Customer customerRequest) {
        System.out.println(customerRequest.toString());

        // Convert CustomerRequest to Customer entity
        Customer customer = new Customer();
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setAddress(customerRequest.getAddress());
        customer.setPhoneno(customerRequest.getPhoneno());

        return customerService.addCustomer(customer);
    }
    
    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteCustomer(@PathVariable("id") Long id) {
        return customerService.deleteCustomer(id);
        //return "Sucessfully deleted for Customer Id "+id;
        
    }
    
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
