package com.jewelbankers.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//import org.springframework.aot.generate.Generated;
//import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@Entity
@Table(name = "customer")
public class Customer {


	@Id
	// @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CUST_SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Customerid")
	private Long id;
	
	public Long getId() {
		return id;
	}

	@Column(name = "area")
	private String area;
	
	@Column(name = "Email")
	private String email;
    
	@Column(name = "Address")
    private String Address;
	
	@Column(name = "State")
	private String phone;
	

	@Column(name = "Pincode")
	private int pincode;

	@Column(name = "Customername")
	private String customerName;
	
	@Column(name = "Street")
	private String street;
    
	@Column(name = "District")
	private String district;
	
//	public List<Bill> getBills() {
//		return bills;
//	}
//
//	public void setBills(List<Bill> bills) {
//		this.bills = bills;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	@Column(name = "Country")
	private String country;
 
	@Column(name = "RelationShip ")
	private String relationShip ;
	
	@Column(name = "RelationShipName ")
	private String relationShipName ;
	 
//	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Bill> bills;
	
	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

	public String getRelationShipName() {
		return relationShipName;
	}

	public void setRelationShipName(String relationShipName) {
		this.relationShipName = relationShipName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	

	
	  public String getEmail() { return email; }
	  
	  public void setEmail(String email) { this.email = email; }
	 

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	// Constructors
	public Customer() {
	}
	

	/*
	 * public Customer(Long id,String name, String email, String phone) { this.name
	 * = name; this.email = email; this.phone = phone; this.id = id; }
	 */

}