package com.jewelbankers.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "sales_item")
public class SalesItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesid", referencedColumnName = "id")
    private Sales sales;
    
    @ManyToOne
    @JoinColumn(name = "purchaseid", referencedColumnName = "id")
    private Purchase purchase;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "purchaseitemid", referencedColumnName = "id")
    private PurchaseItems purchaseItems;
    
    @OneToOne
    @JoinColumn(name = "itemid", referencedColumnName = "id")
    private Item item;
     
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
    
    @Column(name = "rate")
    private double rate;
    
    @Column(name = "makinggcharge")
    private double makinggcharge;
    
    @Column(name = "hallmarkcharges")
    private double hallmarkcharges;
    
    @ManyToOne
    @JoinColumn(name = "barcodeid", referencedColumnName = "id")
    private Barcode barcode;
    
    public Barcode getBarcode() {
		return barcode;
	}

	public void setBarcode(Barcode barcode) {
		this.barcode = barcode;
	}

	public double getHallmarkcharges() {
		return hallmarkcharges;
	}

	public void setHallmarkcharges(double hallmarkcharges) {
		this.hallmarkcharges = hallmarkcharges;
	}

	@Column(name = "gstamount")
    private BigDecimal gstamount;
    
    @Column(name = "amount")
    private BigDecimal amount;
    
    

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

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public PurchaseItems getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(PurchaseItems purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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

	public double getMakinggcharge() {
		return makinggcharge;
	}

	public void setMakinggcharge(double makinggcharge) {
		this.makinggcharge = makinggcharge;
	}

	public BigDecimal getGstamount() {
		return gstamount;
	}

	public void setGstamount(BigDecimal gstamount) {
		this.gstamount = gstamount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
    
}
