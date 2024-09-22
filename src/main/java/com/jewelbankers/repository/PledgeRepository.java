package com.jewelbankers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Pledge;



@Repository
public interface PledgeRepository extends JpaRepository<Pledge, Long> {
    // You can add custom query methods if needed
     // Custom query method to find pledges by customer ID
     List<Pledge> findByCustomerCustomerid(Long customerid);
     boolean existsByBillNo(int billNo);
}
