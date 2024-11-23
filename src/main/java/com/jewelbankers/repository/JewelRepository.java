package com.jewelbankers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Jewel;

@Repository
public interface JewelRepository extends JpaRepository<Jewel, Long> {
    // You can add custom query methods here if needed
	 List<Jewel> findByJewelsequence(Integer jewelsequence);
	
	 @Query("SELECT MAX(jewelno) FROM Jewel")
	 Integer findCurrentJewelno();
}