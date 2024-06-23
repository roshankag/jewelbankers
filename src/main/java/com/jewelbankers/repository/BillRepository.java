package com.jewelbankers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
	List<Bill> findById(int billSequence);
}
