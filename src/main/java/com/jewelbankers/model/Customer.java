package com.jewelbankers.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.annotation.Generated;
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
	@Column(name = "CustomerID")
	private Long id;
	@Column(name = "aREA")
	private String name;
	@Column(name = "CustomerName")
	private String email;

	@Column(name = "State")
	private String phone;


	
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

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}