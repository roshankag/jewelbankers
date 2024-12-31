package com.jewelbankers.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.PurchaseItems;
import com.jewelbankers.repository.PurchaseItemsRepository;

@Service
public class PurchaseItemsService {

    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;

    // Save PurchaseItems (Create/Update)
    public PurchaseItems savePurchaseItems(PurchaseItems purchaseItems) {
        return purchaseItemsRepository.save(purchaseItems);
    }

    // Get PurchaseItems by ID
    public PurchaseItems getPurchaseItemsById(Long id) {
        return purchaseItemsRepository.findById(id).orElse(null);
    }

    // Get all PurchaseItems
    public List<PurchaseItems> getAllPurchaseItems() {
        return purchaseItemsRepository.findAll();
    }

    // Delete PurchaseItems by ID
    public void deletePurchaseItems(Long id) {
        purchaseItemsRepository.deleteById(id);
    }

    // Search PurchaseItems based on criteria
    public List<PurchaseItems> searchPurchaseItems(Map<String, String> searchParams) {
        Specification<PurchaseItems> spec = (root, query, builder) -> {
            if (searchParams != null && !searchParams.isEmpty()) {
                List<Specification<PurchaseItems>> specs = searchParams.entrySet().stream()
                        .map(entry -> {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            
                            // Create specifications based on different search parameters
                            if ("wastagepercent".equals(key)) {
                                return (Specification<PurchaseItems>) (root1, query1, builder1) -> builder1.equal(root1.get("wastagepercent"), Double.parseDouble(value));
                            } else if ("weight".equals(key)) {
                                return (Specification<PurchaseItems>) (root1, query1, builder1) -> builder1.equal(root1.get("weight"), Double.parseDouble(value));
                            } else if ("stoneweight".equals(key)) {
                                return (Specification<PurchaseItems>) (root1, query1, builder1) -> builder1.equal(root1.get("stoneweight"), new BigDecimal(value));
                            } else if ("purity".equals(key)) {
                                return (Specification<PurchaseItems>) (root1, query1, builder1) -> builder1.equal(root1.get("purity"), Double.parseDouble(value));
                            } else if ("rate".equals(key)) {
                                return (Specification<PurchaseItems>) (root1, query1, builder1) -> builder1.equal(root1.get("rate"), new BigDecimal(value));
                            } else if ("amount".equals(key)) {
                                return (Specification<PurchaseItems>) (root1, query1, builder1) -> builder1.equal(root1.get("amount"), new BigDecimal(value));
                            } else if ("totalamount".equals(key)) {
                                return (Specification<PurchaseItems>) (root1, query1, builder1) -> builder1.equal(root1.get("totalamount"), new BigDecimal(value));
                            } else if ("purchase.id".equals(key)) {
                                return (Specification<PurchaseItems>) (root1, query1, builder1) -> builder1.equal(root1.get("purchase").get("id"), Long.parseLong(value));
                            } else if ("item.id".equals(key)) {
                                return (Specification<PurchaseItems>) (root1, query1, builder1) -> builder1.equal(root1.get("item").get("id"), Long.parseLong(value));
                            } else {
                                return null;
                            }
                        })
                        .filter(specification -> specification != null)
                        .collect(Collectors.toList());

                // Combine all specifications using AND if they exist
                if (!specs.isEmpty()) {
                    Specification<PurchaseItems> resultSpec = specs.get(0);
                    for (int i = 1; i < specs.size(); i++) {
                        resultSpec = Specification.where(resultSpec).and(specs.get(i));
                    }
                    return resultSpec.toPredicate(root, query, builder);
                }
            }
            return builder.conjunction(); // Return all records if no filters are applied
        };

        return purchaseItemsRepository.findAll(spec);
    }

}
