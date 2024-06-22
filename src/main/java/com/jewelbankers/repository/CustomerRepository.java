package com.jewelbankers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByCustomerNameIgnoreCaseContaining(String customerName);
	List<Customer> findByCustomerName(String customerName);
	List<Customer> findByStreetIgnoreCaseContaining(String street);
}
