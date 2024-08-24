package com.balaji.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.balaji.springjwt.models.Pledge;

@Repository
public interface PledgeRepository extends JpaRepository<Pledge, Long> {
    // You can add custom query methods if needed
     // Custom query method to find pledges by customer ID
     List<Pledge> findByCustomer_CustomerId(Long customerId);
     boolean existsByBillNo(int billNo);
}
