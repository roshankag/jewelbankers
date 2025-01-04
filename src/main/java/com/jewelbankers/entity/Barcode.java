package com.jewelbankers.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "barcodes")
public class Barcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag")
    private String tag;
    
    @JsonBackReference("barcode")
    @ManyToOne
    @JoinColumn(name = "purchaseitemid", referencedColumnName = "id")
    private PurchaseItems purchaseItems;
    
    @Column(name= "barcodedatetime")
    private LocalDateTime barcodedatetime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public PurchaseItems getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(PurchaseItems purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	public LocalDateTime getBarcodedatetime() {
		return barcodedatetime;
	}

	public void setBarcodedatetime(LocalDateTime barcodedatetime) {
		this.barcodedatetime = barcodedatetime;
	}

}
