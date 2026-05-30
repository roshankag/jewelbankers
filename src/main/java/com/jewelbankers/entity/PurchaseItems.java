package com.jewelbankers.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchaseitems")
public class PurchaseItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	@JsonBackReference("purchase-items")
    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "purchaseitemid")
    @JoinColumn(name = "purchaseid", referencedColumnName = "id")
    private Purchase purchase;

//    //@JsonIgnore // Exclude the full Item object from JSON
//    @OneToOne(cascade = {CascadeType.MERGE})
//    @JoinColumn(name = "itemid", referencedColumnName = "id")
//    private Item item;

    @Column(name = "itemid")
    private long itemid;
    
    @JsonManagedReference("barcode")
    @ManyToOne
    @JoinColumn(name = "barcodeid", referencedColumnName = "id")
    private Barcode barcode;
    
	@Column(name = "wastagepercent")
    private double wastagepercent;
    
    @Column(name = "weight")
    private double weight;
    
    @Column(name = "stoneweight")
    private BigDecimal stoneweight;
    
    @Column(name = "purity")
    private double purity;
    
    @Column(name = "rate")
    private BigDecimal rate;
    
    @Column(name = "amount")
    private BigDecimal amount;
    
    @Column(name = "totalamount")
    private BigDecimal totalamount;
    
    @Column(name = "status")
    private String status;

	public String getStatus() {
		return status;
	}

	@PrePersist
	public void prePersist() {
	    if (this.status == null) {
	        this.status = "P"; // ✅ Set default if not provided
	    }
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
	
	public Barcode getBarcode() {
		return barcode;
	}

	public void setBarcode(Barcode barcode) {
		this.barcode = barcode;
	}

	public double getWastagepercent() {
		return wastagepercent;
	}

	public void setWastagepercent(double wastagepercent) {
		this.wastagepercent = wastagepercent;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public BigDecimal getStoneweight() {
		return stoneweight;
	}

	public void setStoneweight(BigDecimal stoneweight) {
		this.stoneweight = stoneweight;
	}

	public double getPurity() {
		return purity;
	}

	public void setPurity(double purity) {
		this.purity = purity;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}
    
    
}
