package com.jewelbankers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
