package com.jewelbankers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.SupplierWeight;

@Repository
public interface SupplierWeightRepository extends JpaRepository<SupplierWeight, Long>,  JpaSpecificationExecutor<SupplierWeight>  {
}
