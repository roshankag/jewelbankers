package com.jewelbankers.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jewelbankers.entity.Product;
import com.jewelbankers.services.ProductService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	
    @GetMapping(produces = "application/json")    
    public ResponseEntity<List<Product>> findAll(){
     List<Product> allProducts = productService.findAll();
     return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }
	    
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Product> findById(@PathVariable("id") Long id) {
		Optional<Product> product = productService.findById(id);
		return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}