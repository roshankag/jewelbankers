package com.jewelbankers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jewelbankers.entity.BillDetail;

public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {

//	@Query("SELECT DISTINCT b.productDescription FROM BillDetail b WHERE LOWER(b.productDescription) LIKE LOWER(CONCAT(:prefix, '%'))")
//	List<String> findProductDescriptionsByPrefix(@Param("prefix") String prefix, Pageable pageable);
	

	@Query("SELECT DISTINCT bd.productDescription FROM BillDetail bd WHERE bd.productDescription LIKE CONCAT(:prefix, '%')")
	List<String> findProductDescriptionsByPrefix(@Param("prefix") String prefix);
	
}