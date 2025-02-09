package com.jewelbankers.entity;

import java.math.BigDecimal;

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
@Table(name = "sales_item")
public class SalesItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference("sales-items")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesid", referencedColumnName = "id")
    private Sales sales;
    
    //@JsonManagedReference("purchase-item") // Use @JsonManagedReference here
	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "purchaseitemid", referencedColumnName = "id") private
	 * PurchaseItems purchaseItem; // Relationship with PurchaseItems
	 */
	@Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "grosswt")
    private double grosswt;
    
    @Column(name = "netwt")
    private double netwt;
    
    @Column(name = "purity")
    private double purity;
    
    @Column(name = "wastagecharge")
    private double wastagecharge;
    
    @Column(name = "wastagepercent")
    private double wastagepercent;
    
    @Column(name = "rate")
    private double rate;
    
    @Column(name = "makingpercent")
    private double makingpercent;

	@Column(name = "makingcharge")
    private double makingcharge;
    
    @Column(name = "hallmarkcharges")
    private double hallmarkcharges;
    
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "barcodeid", referencedColumnName = "id") private Barcode
	 * barcode;
	 */
    
	/*
	 * @Column(name = "gstamount") private BigDecimal gstamount;
	 */
    
    
    //Set all foreign key mappings in sales table
    @Column(name = "amount")
    private BigDecimal amount;
    
    @Column(name = "itemid")
    private long itemid;
    

	@Column(name = "purchaseid")
    private long purchaseid;
    
    @Column(name = "purchaseitemid")
    private long purchaseitemid;
    
    public long getPurchaseid() {
		return purchaseid;
	}

	public void setPurchaseid(long purchaseid) {
		this.purchaseid = purchaseid;
	}

	public long getPurchaseitemid() {
		return purchaseitemid;
	}

	public void setPurchaseitemid(long purchaseitemid) {
		this.purchaseitemid = purchaseitemid;
	}

    
	/*
	 * public PurchaseItems getPurchaseItem() { return purchaseItem; }
	 * 
	 * public void setPurchaseItem(PurchaseItems purchaseItem) { this.purchaseItem =
	 * purchaseItem; }
	 */

	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public double getWastagepercent() {
		return wastagepercent;
	}

	public void setWastagepercent(double wastagepercent) {
		this.wastagepercent = wastagepercent;
	}

	public double getMakingpercent() {
		return makingpercent;
	}

	public void setMakingpercent(double makingpercent) {
		this.makingpercent = makingpercent;
	}

	public double getHallmarkcharges() {
		return hallmarkcharges;
	}

	public void setHallmarkcharges(double hallmarkcharges) {
		this.hallmarkcharges = hallmarkcharges;
	}    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sales getSales() {
		return sales;
	}

	public void setSales(Sales sales) {
		this.sales = sales;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getGrosswt() {
		return grosswt;
	}

	public void setGrosswt(double grosswt) {
		this.grosswt = grosswt;
	}

	public double getNetwt() {
		return netwt;
	}

	public void setNetwt(double netwt) {
		this.netwt = netwt;
	}

	public double getPurity() {
		return purity;
	}

	public void setPurity(double purity) {
		this.purity = purity;
	}

	public double getWastagecharge() {
		return wastagecharge;
	}

	public void setWastagecharge(double wastagecharge) {
		this.wastagecharge = wastagecharge;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}


	/*
	 * public BigDecimal getGstamount() { return gstamount; }
	 * 
	 * public void setGstamount(BigDecimal gstamount) { this.gstamount = gstamount;
	 * }
	 */

	public double getMakingcharge() {
		return makingcharge;
	}

	public void setMakingcharge(double makingcharge) {
		this.makingcharge = makingcharge;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
    
}
