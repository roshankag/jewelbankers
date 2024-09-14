package com.jewelbankers.services;

import com.jewelbankers.entity.ProductDesc;
import com.jewelbankers.repository.ProductDescRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDescService {

    @Autowired
    private ProductDescRepository productDescRepository;

    public String getProductDescriptionById(Long productSeq) {
        return productDescRepository.findById(productSeq)
            .map(ProductDesc::getProductDescription)
            .orElse("No Description Available");
    }
}
