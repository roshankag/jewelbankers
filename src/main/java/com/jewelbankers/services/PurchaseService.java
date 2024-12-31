package com.jewelbankers.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.Purchase;
import com.jewelbankers.repository.PurchaseRepository;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    // Save new Purchase
    public Purchase savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    // Get Purchase by ID
    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id).orElse(null);
    }

    // Get all Purchases
    public Iterable<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    // Delete Purchase by ID
    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }
    
    public List<Purchase> searchPurchases(Map<String, String> searchParams) {
        Specification<Purchase> spec = (root, query, builder) -> {
            if (searchParams != null && !searchParams.isEmpty()) {
                List<Specification<Purchase>> specs = searchParams.entrySet().stream()
                        .map(entry -> {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            
                            // Create specifications based on different search parameters
                            if ("invoiceno".equals(key)) {
                                return (Specification<Purchase>) (root1, query1, builder1) -> builder1.equal(root1.get("invoiceno"), Long.parseLong(value));
                            } else if ("invoiceprefix".equals(key)) {
                                return (Specification<Purchase>) (root1, query1, builder1) -> builder1.like(root1.get("invoiceprefix"), "%" + value + "%");
                            } else if ("invoicedatetime".equals(key)) {
                                return (Specification<Purchase>) (root1, query1, builder1) -> builder1.equal(root1.get("invoicedatetime"), LocalDateTime.parse(value));
                            } else if ("paymenttype".equals(key)) {
                                return (Specification<Purchase>) (root1, query1, builder1) -> builder1.equal(root1.get("paymenttype"), value);
                            } else if ("comments".equals(key)) {
                                return (Specification<Purchase>) (root1, query1, builder1) -> builder1.like(root1.get("comments"), "%" + value + "%");
                            } else if ("totalamount".equals(key)) {
                                return (Specification<Purchase>) (root1, query1, builder1) -> builder1.equal(root1.get("totalamount"), new BigDecimal(value));
                            } else if ("status".equals(key)) {
                                return (Specification<Purchase>) (root1, query1, builder1) -> builder1.equal(root1.get("status"), value);
                            } else if ("gstamount".equals(key)) {
                                return (Specification<Purchase>) (root1, query1, builder1) -> builder1.equal(root1.get("gstamount"), new BigDecimal(value));
                            } else if ("discount".equals(key)) {
                                return (Specification<Purchase>) (root1, query1, builder1) -> builder1.equal(root1.get("discount"), Double.parseDouble(value));
                            } else if ("supplier.id".equals(key)) {
                                return (Specification<Purchase>) (root1, query1, builder1) -> builder1.equal(root1.get("supplier").get("id"), Long.parseLong(value));
                            } else if ("purchaseItems.id".equals(key)) {
                                return (Specification<Purchase>) (root1, query1, builder1) -> builder1.equal(root1.get("purchaseItems").get("id"), Long.parseLong(value));
                            } else {
                                return null;
                            }
                        })
                        .filter(specification -> specification != null)
                        .collect(Collectors.toList());

                // Combine all specifications using AND if they exist
                if (!specs.isEmpty()) {
                    Specification<Purchase> resultSpec = specs.get(0);
                    for (int i = 1; i < specs.size(); i++) {
                        resultSpec = Specification.where(resultSpec).and(specs.get(i));
                    }
                    return resultSpec.toPredicate(root, query, builder);
                }
            }
            return builder.conjunction(); // Return all records if no filters are applied
        };

        return purchaseRepository.findAll(spec);
    }


}
