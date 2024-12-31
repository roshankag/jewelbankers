package com.jewelbankers.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.Sales;
import com.jewelbankers.repository.SalesRepository;

@Service
public class SalesService {

    @Autowired
    private SalesRepository salesRepository;

    public Sales saveSales(Sales sales) {
        return salesRepository.save(sales);
    }

    public Sales getSalesById(Long id) {
        Optional<Sales> sales = salesRepository.findById(id);
        return sales.orElse(null);
    }

    public void deleteSales(Long id) {
        salesRepository.deleteById(id);
    }

    public List<Sales> getAllSales() {
        return salesRepository.findAll();
    }

    public List<Sales> searchSales(Map<String, String> searchParams) {
        Specification<Sales> spec = (root, query, builder) -> {
            if (searchParams != null && !searchParams.isEmpty()) {
                List<Specification<Sales>> specs = searchParams.entrySet().stream()
                        .map(entry -> {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            
                            // Create specifications based on different search parameters
                            if ("billno".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("billno"), Long.parseLong(value));
                            } else if ("billprefix".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.like(root1.get("billprefix"), "%" + value + "%");
                            } else if ("billdate".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("billdate"), LocalDateTime.parse(value));
                            } else if ("paymenttype".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("paymenttype"), value);
                            } else if ("salesman".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.like(root1.get("salesman"), "%" + value + "%");
                            } else if ("billduedatetime".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("billduedatetime"), LocalDateTime.parse(value));
                            } else if ("billamount".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("billamount"), new BigDecimal(value));
                            } else if ("gstamount".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("gstamount"), Double.parseDouble(value));
                            } else if ("billdiscount".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("billdiscount"), Double.parseDouble(value));
                            } else if ("totalamount".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("totalamount"), Double.parseDouble(value));
                            } else if ("paymentsubtype".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("paymentsubtype"), value);
                            } else if ("paymentrefno".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("paymentrefno"), value);
                            } else if ("paytmentrecieved".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("paytmentrecieved"), new BigDecimal(value));
                            } else if ("paymentbalanceamt".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("paymentbalanceamt"), new BigDecimal(value));
                            } else if ("paymentseqno".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("paymentseqno"), Long.parseLong(value));
                            } else if ("customer.id".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("customer").get("id"), Long.parseLong(value));
                            } else if ("salesitems.id".equals(key)) {
                                return (Specification<Sales>) (root1, query1, builder1) -> builder1.equal(root1.get("salesitems").get("id"), Long.parseLong(value));
                            } else {
                                return null;
                            }
                        })
                        .filter(specification -> specification != null)
                        .collect(Collectors.toList());

                // Combine all specifications using AND if they exist
                if (!specs.isEmpty()) {
                    Specification<Sales> resultSpec = specs.get(0);
                    for (int i = 1; i < specs.size(); i++) {
                        resultSpec = Specification.where(resultSpec).and(specs.get(i));
                    }
                    return resultSpec.toPredicate(root, query, builder);
                }
            }
            return builder.conjunction(); // Return all records if no filters are applied
        };

        return salesRepository.findAll(spec);
    }

}
