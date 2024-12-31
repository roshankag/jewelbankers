package com.jewelbankers.Utility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import com.jewelbankers.entity.Barcode;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SearchSpecification<T> {

    public static Specification<Barcode> filterByFullSearch(LocalDate startDate, LocalDate endDate, Integer itemTypeNo) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            // Add date range condition if startDate or endDate is provided
            if (startDate != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("barcodeCreatedDate"), startDate));
            }

            if (endDate != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("barcodeCreatedDate"), endDate));
            }

            // Add itemTypeNo condition if itemTypeNo is provided
            if (itemTypeNo != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("itemMaster").get("itemTypeNo"), itemTypeNo));
            }

            return predicate;
        };
    }
}
