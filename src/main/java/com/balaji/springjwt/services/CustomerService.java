package com.balaji.springjwt.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balaji.springjwt.models.Customer;
import com.balaji.springjwt.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer addCustomer(Customer customer) {
        System.out.println(customer.toString());
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        if (customerRepository.existsById(id)) {
            return customerRepository.save(updatedCustomer);
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
