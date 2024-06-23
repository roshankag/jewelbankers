package com.jewelbankers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
}
