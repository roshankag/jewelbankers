package com.jewelbankers.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.SalesItems;
import com.jewelbankers.repository.SalesItemsRepository;

@Service
public class SalesItemsService {

    @Autowired
    private SalesItemsRepository salesItemsRepository;

    // Save Sales Item
    public SalesItems saveSalesItem(SalesItems salesItem) {
        return salesItemsRepository.save(salesItem);
    }

    // Get all Sales Items
    public List<SalesItems> getAllSalesItems() {
        return salesItemsRepository.findAll();
    }

    // Get Sales Item by ID
    public SalesItems getSalesItemById(Long id) {
        return salesItemsRepository.findById(id).orElse(null);
    }

    // Delete Sales Item
    public void deleteSalesItem(Long id) {
        salesItemsRepository.deleteById(id);
    }
    
    public List<SalesItems> searchSalesItems(Map<String, String> searchParams) {
        Specification<SalesItems> spec = (root, query, builder) -> {
            if (searchParams != null && !searchParams.isEmpty()) {
                List<Specification<SalesItems>> specs = searchParams.entrySet().stream()
                        .map(entry -> {
                            String key = entry.getKey();
                            String value = entry.getValue();

                            // Create specifications based on different search parameters
                            if ("tagno".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.like(root1.get("tagno"), "%" + value + "%");
                            } else if ("quantity".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("quantity"), Integer.parseInt(value));
                            } else if ("grosswt".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("grosswt"), Double.parseDouble(value));
                            } else if ("netwt".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("netwt"), Double.parseDouble(value));
                            } else if ("purity".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("purity"), Double.parseDouble(value));
                            } else if ("wastagecharge".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("wastagecharge"), Double.parseDouble(value));
                            } else if ("rate".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("rate"), Double.parseDouble(value));
                            } else if ("makinggcharge".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("makinggcharge"), Double.parseDouble(value));
                            } else if ("gstamount".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("gstamount"), new BigDecimal(value));
                            } else if ("amount".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("amount"), new BigDecimal(value));
                            } else if ("sales.id".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("sales").get("id"), Long.parseLong(value));
                            } else if ("purchase.id".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("purchase").get("id"), Long.parseLong(value));
                            } else if ("purchaseItems.id".equals(key)) {
                                return (Specification<SalesItems>) (root1, query1, builder1) -> builder1.equal(root1.get("purchaseItems").get("id"), Long.parseLong(value));
                            } else {
                                return null;
                            }
                        })
                        .filter(specification -> specification != null)
                        .collect(Collectors.toList());

                // Combine all specifications using AND if they exist
                if (!specs.isEmpty()) {
                    Specification<SalesItems> resultSpec = specs.get(0);
                    for (int i = 1; i < specs.size(); i++) {
                        resultSpec = Specification.where(resultSpec).and(specs.get(i));
                    }
                    return resultSpec.toPredicate(root, query, builder);
                }
            }
            return builder.conjunction(); // Return all records if no filters are applied
        };

        return salesItemsRepository.findAll(spec);
    }


 
}
