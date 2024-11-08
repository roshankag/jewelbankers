package com.jewelbankers.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "jewel_header")
public class Jewel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jewelsequence") 
    private Integer jewelsequence; 
	
	@Column(name = "jewelno")
	private Integer jewelno;
	
	@Column(name = "jeweldate")
    private Date jeweldate;   // Date of the bill

    @OneToOne(fetch = FetchType.EAGER,  cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;
    
    @Column(name = "itemtypeno")
    private Integer itemtypeno;
    
    @Column(name = "totalamount")
    private Integer totalamount;
    
    @Column(name = "weight")
    private BigDecimal weight; // Gross weight of the item (in grams)
    
    @Column(name = "makingcharge")
    private BigDecimal makingcharge; // Making charge (calculated based on percentage)
    
    @Column(name = "wastagecharge")
    private BigDecimal wastagecharge; // Wastage charge (calculated based on percentage)
    
    @OneToMany(mappedBy = "jewel",  cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true, fetch = FetchType.LAZY)
   	@JsonManagedReference
   	private List<JewelDetail> jeweldetails;
    
    public List<JewelDetail> getJewelDetails() {
		return jeweldetails;
	}
    
    public void setJewelDetails(List<JewelDetail> jeweldetails) {
		this.jeweldetails=jeweldetails;
    }
    
    public void removeBillDetail(JewelDetail detail) {
        if (jeweldetails.remove(detail)) {
            detail.setJewel(null);  // Clear the back-reference
        }
    }

	public Integer getJewelsequence() {
		return jewelsequence;
	}

	public void setJewelsequence(Integer jewelsequence) {
		this.jewelsequence = jewelsequence;
	}

	public Integer getJewelno() {
		return jewelno;
	}

	public void setJewelno(Integer jewelno) {
		this.jewelno = jewelno;
	}

	public Date getJeweldate() {
		return jeweldate;
	}

	public void setJeweldate(Date jeweldate) {
		this.jeweldate = jeweldate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getItemtypeno() {
		return itemtypeno;
	}

	public void setItemtypeno(Integer itemtypeno) {
		this.itemtypeno = itemtypeno;
	}

	public Integer getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Integer totalamount) {
		this.totalamount = totalamount;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getMakingcharge() {
		return makingcharge;
	}

	public void setMakingcharge(BigDecimal makingcharge) {
		this.makingcharge = makingcharge;
	}

	public BigDecimal getWastagecharge() {
		return wastagecharge;
	}

	public void setWastagecharge(BigDecimal wastagecharge) {
		this.wastagecharge = wastagecharge;
	}

	public List<JewelDetail> getJeweldetails() {
		return jeweldetails;
	}

	public void setJeweldetails(List<JewelDetail> jeweldetails) {
		this.jeweldetails = jeweldetails;
	}
    
    
}
