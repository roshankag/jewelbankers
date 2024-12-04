package com.jewelbankers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>,  JpaSpecificationExecutor<Supplier>  {
}
