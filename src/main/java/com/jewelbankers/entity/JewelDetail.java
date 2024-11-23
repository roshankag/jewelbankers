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
@Table(name = "jewel_detail")
public class JewelDetail {
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "jewelsequence", referencedColumnName = "jewelsequence")
    private Jewel jewel;
	
	@Id
    @Column(name = "itemno")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemno;

    @Column(name = "itemdescription", length = 16000)
    private String itemdescription;

    @Column(name = "itemquantity")
    private Integer itemquantity;
    
    @Column(name = "barcode")
    private String barcode;
    
    public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getItemno() {
		return itemno;
	}

	public void setItemno(Integer itemno) {
		this.itemno = itemno;
	}

	public String getItemdescription() {
		return itemdescription;
	}

	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
	}

	public Integer getItemquantity() {
		return itemquantity;
	}

	public void setItemquantity(Integer itemquantity) {
		this.itemquantity = itemquantity;
	}

	public Jewel getJewel() {
		return jewel;
	}

	public void setJewel(Jewel jewel) {
		this.jewel = jewel;
	}

}
