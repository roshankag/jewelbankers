package com.jewelbankers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jewelbankers.entity.BillDetail;

public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {

//	@Query("SELECT DISTINCT b.productDescription FROM BillDetail b WHERE LOWER(b.productDescription) LIKE LOWER(CONCAT(:prefix, '%'))")
//	List<String> findProductDescriptionsByPrefix(@Param("prefix") String prefix, Pageable pageable);
	

	 @Query(value = """
		        WITH RECURSIVE split_cte AS (
		            SELECT 
		                bill_sequence AS billSequence,
		                TRIM(SUBSTRING_INDEX(product_description, ',', 1)) AS description,
		                CASE 
		                    WHEN LOCATE(',', product_description) > 0 THEN 
		                        SUBSTRING(product_description, LOCATE(',', product_description) + 1)
		                    ELSE NULL
		                END AS remaining
		            FROM bill_detail

		            UNION ALL

		            SELECT 
		                billSequence,
		                TRIM(SUBSTRING_INDEX(remaining, ',', 1)) AS description,
		                CASE 
		                    WHEN LOCATE(',', remaining) > 0 THEN 
		                        SUBSTRING(remaining, LOCATE(',', remaining) + 1)
		                    ELSE NULL
		                END AS remaining
		            FROM split_cte
		            WHERE remaining IS NOT NULL
		        )
		        SELECT DISTINCT description
		        FROM split_cte
		        WHERE TRIM(description) <> '' AND description LIKE CONCAT(:prefix, '%')
		    """, nativeQuery = true)
		    List<String> findProductDescriptionsByPrefix(@Param("prefix") String prefix);
	
}