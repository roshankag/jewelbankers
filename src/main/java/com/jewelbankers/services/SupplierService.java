package com.jewelbankers.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.Supplier;
import com.jewelbankers.enums.BalanceType;
import com.jewelbankers.repository.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
    
    public List<Supplier> searchSuppliers(Map<String, String> searchParams) {
        Specification<Supplier> spec = (root, query, builder) -> {
            if (searchParams != null && !searchParams.isEmpty()) {
                List<Specification<Supplier>> specs = searchParams.entrySet().stream()
                        .map(entry -> {
                            String key = entry.getKey();
                            String value = entry.getValue();

                            // Create specifications based on different search parameters
                            if ("suppliername".equals(key)) {
                                return (Specification<Supplier>) (root1, query1, builder1) -> builder1.like(root1.get("suppliername"), "%" + value + "%");
                            } else if ("address".equals(key)) {
                                return (Specification<Supplier>) (root1, query1, builder1) -> builder1.like(root1.get("address"), "%" + value + "%");
                            } else if ("gstno".equals(key)) {
                                return (Specification<Supplier>) (root1, query1, builder1) -> builder1.like(root1.get("gstno"), "%" + value + "%");
                            } else if ("mobile".equals(key)) {
                                return (Specification<Supplier>) (root1, query1, builder1) -> builder1.like(root1.get("mobile"), "%" + value + "%");
                            } else if ("email".equals(key)) {
                                return (Specification<Supplier>) (root1, query1, builder1) -> builder1.like(root1.get("email"), "%" + value + "%");
                            } else if ("prooftype".equals(key)) {
                                return (Specification<Supplier>) (root1, query1, builder1) -> builder1.equal(root1.get("prooftype"), value.charAt(0));
                            } else if ("openingbalance".equals(key)) {
                                return (Specification<Supplier>) (root1, query1, builder1) -> builder1.equal(root1.get("openingbalance"), new BigDecimal(value));
                            } else if ("openingbaltype".equals(key)) {
                                return (Specification<Supplier>) (root1, query1, builder1) -> builder1.equal(root1.get("openingbaltype"), BalanceType.valueOf(value));
                            } else {
                                return null;
                            }
                        })
                        .filter(specification -> specification != null)
                        .collect(Collectors.toList());

                // Combine all specifications using AND if they exist
                if (!specs.isEmpty()) {
                    Specification<Supplier> resultSpec = specs.get(0);
                    for (int i = 1; i < specs.size(); i++) {
                        resultSpec = Specification.where(resultSpec).and(specs.get(i));
                    }
                    return resultSpec.toPredicate(root, query, builder);
                }
            }
            return builder.conjunction(); // Return all records if no filters are applied
        };

        return supplierRepository.findAll(spec);
    }

}
