package com.jewelbankers.controller;

import com.jewelbankers.entity.Customer;
import com.jewelbankers.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }
    
    @PostMapping
    @ResponseBody
    public Customer saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

//(@PathVariable("id") Long id)
    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable("id") Long id) {
        return customerService.findById(id);
        /*return customer.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());*/
    }
}
