package com.jewelbankers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByCustomerNameIgnoreCaseContaining(String customerName);
	List<Customer> findByCustomerName(String customerName);
	List<Customer> findByStreetIgnoreCaseContaining(String street);
	List<Customer> findByCustomerNameStartingWithIgnoreCase(String customerName);
	
	@Query("SELECT c FROM Customer c WHERE str(c.phoneno) LIKE CONCAT(:phoneno, '%')")
    List<Customer> findByPhonenoStartingWith(@Param("phoneno") Long phoneno);
	 
	List<Customer> findByCustomerNameStartingWithIgnoreCaseAndPhoneno(String customerName, Long phoneno);
	
}
