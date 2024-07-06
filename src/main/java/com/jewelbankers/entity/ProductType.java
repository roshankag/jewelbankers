package com.jewelbankers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_type")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_TYPE_NO")
    private int productTypeNo;
    
    @Column(name = "PRODUCT_TYPE_CODE")
    private String productTypeCode;

    @Column(name = "PRODUCT_TYPE_DESCRIPTION")
    private String productTypeDescription;

    @Column(name = "RATE_OF_INTEREST")
    private double rateOfInterest;

    public int getProductTypeNo() {
		return productTypeNo;
	}

	public void setProductTypeNo(int productTypeNo) {
		this.productTypeNo = productTypeNo;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public String getProductTypeDescription() {
		return productTypeDescription;
	}

	public void setProductTypeDescription(String productTypeDescription) {
		this.productTypeDescription = productTypeDescription;
	}

	public double getRateOfInterest() {
		return rateOfInterest;
	}

	public void setRateOfInterest(double rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}
}