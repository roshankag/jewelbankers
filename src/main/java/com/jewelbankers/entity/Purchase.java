package com.jewelbankers.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "supplierid", referencedColumnName = "id")
    private Supplier supplier;
    
    @JsonManagedReference("purchase-items")  // Unique reference name 
    //@JoinColumn(name = "purchaseitemid")
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<PurchaseItems> purchaseItems = new ArrayList<>();

    @Column(name = "invoiceno")
    private Long invoiceno;
    
    @Column(name = "invoiceprefix")
    private String invoiceprefix;
    
    @Column(name = "invoicedatetime")
    private LocalDateTime invoicedatetime;
    
    @Column(name = "paymenttype")
    private String paymenttype;
    
    @Column(name = "comments")
    private String comments;

    @Column(name = "totalamount")
    private BigDecimal totalamount;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "gstamount")
    private BigDecimal gstamount;
    
    @Column(name = "discount")
    private double discount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public List<PurchaseItems> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(List<PurchaseItems> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	public Long getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(Long invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getInvoiceprefix() {
		return invoiceprefix;
	}

	public void setInvoiceprefix(String invoiceprefix) {
		this.invoiceprefix = invoiceprefix;
	}

	public LocalDateTime getInvoicedatetime() {
		return invoicedatetime;
	}

	public void setInvoicedatetime(LocalDateTime invoicedatetime) {
		this.invoicedatetime = invoicedatetime;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getGstamount() {
		return gstamount;
	}

	public void setGstamount(BigDecimal gstamount) {
		this.gstamount = gstamount;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
    
}
