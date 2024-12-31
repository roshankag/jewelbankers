package com.jewelbankers.repository;

import com.jewelbankers.entity.SalesItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesItemsRepository extends JpaRepository<SalesItems, Long>, JpaSpecificationExecutor<SalesItems> {
    // Additional query methods can be defined here if required
}
