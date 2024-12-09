package com.jewelbankers.Utility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import com.jewelbankers.entity.Barcode;
import com.jewelbankers.entity.ItemMaster;

import jakarta.persistence.criteria.Predicate;

public class SearchSpecification<T> {

    public Specification<T> getSearchSpecification(Map<String, String> search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            search.forEach((key, value) -> {
                if (value != null && !value.isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(key)), "%" + value.toLowerCase() + "%"
                    ));
                }
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    
    public static Specification<ItemMaster> searchByTerm(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("itemName")), "%" + search.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("purity")), "%" + search.toLowerCase() + "%")
        );
    }
    
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
