package com.jewelbankers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.BillUpload;



@Repository
public interface BillUploadRepository extends JpaRepository<BillUpload, Long> {

    // You can add custom query methods if needed
}