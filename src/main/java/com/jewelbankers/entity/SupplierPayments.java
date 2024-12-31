package com.jewelbankers.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "supplier_payments")
public class SupplierPayments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseid", referencedColumnName = "id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "supplierid", referencedColumnName = "id")
    private Supplier supplier;

    @Column(name = "paymenttype")
    private String paymenttype;
    
    @Column(name = "metalitemid")
    private Long metalitemid;
    
    @Column(name = "amount")
    private Integer amount;
    
    @Column(name = "referenceid")
    private String referenceid;
    
    @Column(name = "bankname")
    private String bankname;
    
    @Column(name = "metalwt")
    private BigDecimal metalwt;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public Long getMetalitemid() {
		return metalitemid;
	}

	public void setMetalitemid(Long metalitemid) {
		this.metalitemid = metalitemid;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getReferenceid() {
		return referenceid;
	}

	public void setReferenceid(String referenceid) {
		this.referenceid = referenceid;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public BigDecimal getMetalwt() {
		return metalwt;
	}

	public void setMetalwt(BigDecimal metalwt) {
		this.metalwt = metalwt;
	}
    
}
