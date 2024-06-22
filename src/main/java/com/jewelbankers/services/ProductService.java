package com.jewelbankers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.Product;
import com.jewelbankers.repository.ProductRepository;

@Service
//@Component
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    
    public ProductService() {
    	
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }
//    @Bean
//    public ProductRepository beanExample() {
//    	this.productRepository= new ProductRepository();
//        return this.productRepository;
//    }
}