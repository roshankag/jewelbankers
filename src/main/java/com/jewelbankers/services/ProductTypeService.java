package com.jewelbankers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.ProductType;
import com.jewelbankers.repository.ProductTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    public ProductType getProductTypeById(Long id) {
        return productTypeRepository.findById(id).orElse(null);
    }

    public ProductType createProductType(ProductType productType) {
        return productTypeRepository.save(productType);
    }

    public ProductType updateProductType(Long id, ProductType productTypeDetails) {
        ProductType productType = productTypeRepository.findById(id).orElse(null);

        if (productType != null) {
            productType.setProductTypeCode(productTypeDetails.getProductTypeCode());
            productType.setProductTypeDescription(productTypeDetails.getProductTypeDescription());
            productType.setRateOfInterest(productTypeDetails.getRateOfInterest());
            return productTypeRepository.save(productType);
        }

        return null;
    }

    public boolean deleteProductType(Long id) {
        Optional<ProductType> optionalProductType = productTypeRepository.findById(id);
        if (optionalProductType.isPresent()) {
            productTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

