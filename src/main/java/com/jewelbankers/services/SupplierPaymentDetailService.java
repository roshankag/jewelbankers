package com.jewelbankers.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.SupplierPayments;
import com.jewelbankers.repository.SupplierPaymentDetailRepository;

@Service
public class SupplierPaymentDetailService {

    @Autowired
    private SupplierPaymentDetailRepository supplierPaymentDetailRepository;

    public List<SupplierPayments> getAllSupplierPaymentDetails() {
        return supplierPaymentDetailRepository.findAll();
    }

    public SupplierPayments getSupplierPaymentDetailById(Integer id) {
        return supplierPaymentDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier Payment Detail not found"));
    }

    public SupplierPayments saveSupplierPaymentDetail(SupplierPayments detail) {
        return supplierPaymentDetailRepository.save(detail);
    }

    public void deleteSupplierPaymentDetail(Integer id) {
        supplierPaymentDetailRepository.deleteById(id);
    }

    public List<SupplierPayments> searchSupplierPaymentDetails(Map<String, String> searchParams) {
        Specification<SupplierPayments> spec = (root, query, builder) -> {
            if (searchParams != null && !searchParams.isEmpty()) {
                List<Specification<SupplierPayments>> specs = searchParams.entrySet().stream()
                        .map(entry -> {
                            String key = entry.getKey();
                            String value = entry.getValue();

                            // Create specifications based on different search parameters
                            if ("purchaseseqno".equals(key)) {
                                return (Specification<SupplierPayments>) (root1, query1, builder1) -> builder1.equal(root1.get("purchaseseqno"), Long.parseLong(value));
                            } else if ("paymenttype".equals(key)) {
                                return (Specification<SupplierPayments>) (root1, query1, builder1) -> builder1.like(root1.get("paymenttype"), "%" + value + "%");
                            } else if ("metalitemid".equals(key)) {
                                return (Specification<SupplierPayments>) (root1, query1, builder1) -> builder1.equal(root1.get("metalitemid"), Long.parseLong(value));
                            } else if ("amount".equals(key)) {
                                return (Specification<SupplierPayments>) (root1, query1, builder1) -> builder1.equal(root1.get("amount"), Integer.parseInt(value));
                            } else if ("referenceid".equals(key)) {
                                return (Specification<SupplierPayments>) (root1, query1, builder1) -> builder1.like(root1.get("referenceid"), "%" + value + "%");
                            } else if ("bankname".equals(key)) {
                                return (Specification<SupplierPayments>) (root1, query1, builder1) -> builder1.like(root1.get("bankname"), "%" + value + "%");
                            } else if ("metalwt".equals(key)) {
                                return (Specification<SupplierPayments>) (root1, query1, builder1) -> builder1.equal(root1.get("metalwt"), new BigDecimal(value));
                            } else if ("supplier.id".equals(key)) {
                                return (Specification<SupplierPayments>) (root1, query1, builder1) -> builder1.equal(root1.get("supplier").get("id"), Long.parseLong(value));
                            } else {
                                return null;
                            }
                        })
                        .filter(specification -> specification != null)
                        .collect(Collectors.toList());

                // Combine all specifications using AND if they exist
                if (!specs.isEmpty()) {
                    Specification<SupplierPayments> resultSpec = specs.get(0);
                    for (int i = 1; i < specs.size(); i++) {
                        resultSpec = Specification.where(resultSpec).and(specs.get(i));
                    }
                    return resultSpec.toPredicate(root, query, builder);
                }
            }
            return builder.conjunction(); // Return all records if no filters are applied
        };

        return supplierPaymentDetailRepository.findAll(spec);
    }

}
