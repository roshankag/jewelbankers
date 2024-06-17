package com.jewelbankers.repository;

import com.jewelbankers.model.Customer;

import java.util.List;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}//extends JpaRepository<Customer, Long> {
    // Custom query methods can be added here if needed in the future.
}
