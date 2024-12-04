package com.jewelbankers.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;

import com.jewelbankers.entity.ItemMaster;

import jakarta.persistence.criteria.Predicate;

public class SearchSpecification<T> {

    public Specification<T> getSearchSpecification(Map<String, String> params) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            params.forEach((key, value) -> {
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
}
