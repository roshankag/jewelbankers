package com.jewelbankers.repository;

import com.jewelbankers.entity.PurchaseItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseItemsRepository extends JpaRepository<PurchaseItems, Long>, JpaSpecificationExecutor<PurchaseItems> {
    // You can add custom query methods if needed
}
