package com.jewelbankers.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jewelbankers.Utility.SearchSpecification;
import com.jewelbankers.entity.SupplierWeight;
import com.jewelbankers.repository.SupplierWeightRepository;

@Service
public class SupplierWeightService {

    @Autowired
    private SupplierWeightRepository supplierWeightRepository;

    public List<SupplierWeight> getAllSupplierWeights() {
        return supplierWeightRepository.findAll();
    }

    public SupplierWeight getSupplierWeightById(Long id) {
        return supplierWeightRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier Weight not found"));
    }

    public SupplierWeight saveSupplierWeight(SupplierWeight supplierWeight) {
        return supplierWeightRepository.save(supplierWeight);
    }

    public void deleteSupplierWeight(Long id) {
        supplierWeightRepository.deleteById(id);
    }
    
    public List<SupplierWeight> searchSupplierWeights(Map<String, String> searchParams) {
        SearchSpecification<SupplierWeight> specification = new SearchSpecification<>();
        Specification<SupplierWeight> spec = specification.getSearchSpecification(searchParams);
        return supplierWeightRepository.findAll(spec);
    }
}
