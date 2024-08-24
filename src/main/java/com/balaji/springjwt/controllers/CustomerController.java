package com.balaji.springjwt.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.balaji.springjwt.models.Customer;
import com.balaji.springjwt.payload.request.CustomerAddRequest;
import com.balaji.springjwt.services.CustomerService;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public Customer addCustomer(@RequestBody CustomerAddRequest customerRequest) {
        System.out.println(customerRequest.toString());

        // Convert CustomerRequest to Customer entity
        Customer customer = new Customer();
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setFullAddress(customerRequest.getFullAddress());
        customer.setPhone(customerRequest.getPhone());

        return customerService.addCustomer(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        try {
            Customer customer = customerService.updateCustomer(id, updatedCustomer);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
