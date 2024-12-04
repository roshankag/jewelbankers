package com.jewelbankers.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jewelbankers.Utility.SearchSpecification;
import com.jewelbankers.entity.Supplier;
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
        SearchSpecification<Supplier> specification = new SearchSpecification<>();
        Specification<Supplier> spec = specification.getSearchSpecification(searchParams);
        return supplierRepository.findAll(spec);
    }
}
