package com.jewelbankers.Utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.ProductType;
import com.jewelbankers.services.ProductTypeService;

@Service
public class ProductTypeUtility {
	
	@Autowired
	 private ProductTypeService productTypeService;
	
	public Map<String, ProductType> getmap() {
        Map<String, ProductType> productTypeMap = new HashMap<>();
        for (ProductType productType : 	    productTypeService.getAllProductTypes()) {
        	productTypeMap.put(productType.getProductTypeCode(),productType);
        }
        return productTypeMap;
    }

}
