package com.jewelbankers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.SupplierPayments;

@Repository
public interface SupplierPaymentDetailRepository extends JpaRepository<SupplierPayments, Integer>, JpaSpecificationExecutor<SupplierPayments> {
}
