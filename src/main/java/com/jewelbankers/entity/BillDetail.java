package com.jewelbankers.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity

@Table(name = "bill_detail")
public class BillDetail {

    //@Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JoinColumn(name = "BILL_SEQUENCE", referencedColumnName = "billSequence",insertable = true, updatable = true)
    //@Column(name = "BILL_SEQUENCE") 
    //private int billSequence;
    
    @Id
    @Column(name = "PRODUCT_NO")
    private int productNo;

    @Column(name = "PRODUCT_DESCRIPTION", length = 2000)
    private String productDescription;

    @Column(name = "PRODUCT_QUANTITY")
    private int productQuantity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "BILL_SEQUENCE", referencedColumnName = "billSequence")
    //@JoinColumn(name = "BILL_SEQUENCE")
    private Bill bill;

    public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	/*
	 * public int getBillSequence() { return billSequence; }
	 * 
	 * public void setBillSequence(int billSequence) { this.billSequence =
	 * billSequence; }
	 */

	public int getProductNo() {
		return productNo;
	}

	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
}
    