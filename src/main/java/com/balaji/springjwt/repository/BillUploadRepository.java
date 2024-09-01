package com.balaji.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.balaji.springjwt.models.BillUpload;

@Repository
public interface BillUploadRepository extends JpaRepository<BillUpload, Long> {

    // You can add custom query methods if needed
}