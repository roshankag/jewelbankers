package com.jewelbankers.repository;

import com.jewelbankers.entity.ProductDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDescRepository extends JpaRepository<ProductDesc, Long> {
    // No additional methods are required as we can use the built-in findById method
}
