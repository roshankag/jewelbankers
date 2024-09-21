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
	@Column(name = "customerid")
	private Long customerid;
	
	
	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}


	@Column(name = "area")
	private String area;
	
	@Column(name = "mailid")
	private String mailid;
    
	@Column(name = "Address")
    private String Address;
	
	@Column(name = "State")
	private String state;
	

	@Column(name = "Pincode")
	private int pincode;

	@Column(name = "Customername")
	private String customerName;
	
	@Column(name = "Photo")
    private String photo;
	
	@Column(name = "proof_type")
    private Character proofType;

    public Character getProofType() {
		return proofType;
	}

	public void setProofType(Character proofType) {
		this.proofType = proofType;
	}

	public String getProofDetails() {
		return proofDetails;
	}

	public void setProofDetails(String proofDetails) {
		this.proofDetails = proofDetails;
	}


	@Column(name = "proof_details")
    private String proofDetails;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}


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
 
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getRelationshipname() {
		return relationshipname;
	}

	public void setRelationshipname(String relationshipname) {
		this.relationshipname = relationshipname;
	}

	public Long getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(Long phoneno) {
		this.phoneno = phoneno;
	}

	public Long getMobileno() {
		return mobileno;
	}

	public void setMobileno(Long mobileno) {
		this.mobileno = mobileno;
	}


	@Column(name = "relationship ")
	private String relationship ;
	
	@Column(name = "relationname ")
	private String relationshipname ;
	

	@Column(name = "phoneno")
	private Long phoneno;
	
	@Column(name = "mobileno")
	private Long mobileno;
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

	

	public String getMailid() {
		return mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	// Constructors
	public Customer() {
	}
	

	/*
	 * public Customer(Long id,String name, String email, String phone) { this.name
	 * = name; this.email = email; this.phone = phone; this.id = id; }
	 */

}