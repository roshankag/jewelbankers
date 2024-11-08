package com.jewelbankers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.JewelDetail;

@Repository
public interface JewelDetailRepository extends JpaRepository<JewelDetail, Integer> {
    Optional<JewelDetail> findByBarcode(String barcode);
}

