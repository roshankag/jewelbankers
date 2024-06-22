package com.jewelbankers.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.Product;

@Repository
//@Component
public class ProductRepository {
    private final List<Product> products;

    public ProductRepository(){
        this.products = buildFakeProducts();
    }

    private List<Product> buildFakeProducts(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Leaf Rake");
        p1.setPrice(20.12);
        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Garden Cart");
        p2.setPrice(40.99);
        Product p3 = new Product();
        p3.setId(3L);
        p3.setName("Hammer");
        p3.setPrice(7.98);
        Product p4 = new Product();
        p4.setId(5L);
        p4.setName("Saw");
        p4.setPrice(14.56);
        Product p5 = new Product();
        p5.setId(6L);
        p5.setName("Video Game Controller");
        p5.setPrice(39.94);

        return List.of(p1,p2,p3,p4,p5);
    }
    public List<Product> findAll(){
        return this.products;
    }

    public Optional<Product> findById(Long id){
        Optional<Product> ret = Optional.empty();
        List<Product> filteredProducts = this.products.stream().filter(p -> Objects.equals(p.getId(), id)).collect(Collectors.toList());
        if(!filteredProducts.isEmpty()){
            ret = Optional.of(filteredProducts.get(0));
        }
        return ret;
    }
}
