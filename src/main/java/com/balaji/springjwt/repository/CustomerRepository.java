package com.balaji.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.balaji.springjwt.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // You can add custom query methods if needed
}
