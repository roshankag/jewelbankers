package com.jewelbankers.entity;

import java.math.BigDecimal;
import java.util.List;

import com.jewelbankers.enums.BalanceType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "suppliername", nullable = false, length = 255)
    private String suppliername;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "gstno", length = 50)
    private String gstno;

    @Column(name = "mobile", length = 15)
    private String mobile;

    @Column(name = "email", length = 255)
    private String email;
    
    @Column(name = "prooftype")
    private Character prooftype;
    
    @Column(name = "proofdetails")
    private String proofdetails;

    @Column(name = "openingbalance")
    private BigDecimal openingbalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "openingbaltype", length = 10)
    private BalanceType openingbaltype;

    public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGstno() {
		return gstno;
	}

	public void setGstno(String gstno) {
		this.gstno = gstno;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Character getProoftype() {
		return prooftype;
	}

	public void setProoftype(Character prooftype) {
		this.prooftype = prooftype;
	}

	public String getProofdetails() {
		return proofdetails;
	}

	public void setProofdetails(String proofdetails) {
		this.proofdetails = proofdetails;
	}

	public BigDecimal getOpeningbalance() {
		return openingbalance;
	}

	public void setOpeningbalance(BigDecimal openingbalance) {
		this.openingbalance = openingbalance;
	}

	public BalanceType getOpeningbaltype() {
		return openingbaltype;
	}

	public void setOpeningbaltype(BalanceType openingbaltype) {
		this.openingbaltype = openingbaltype;
	}

	public String getOpeningdetails() {
		return openingdetails;
	}

	public void setOpeningdetails(String openingdetails) {
		this.openingdetails = openingdetails;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	@Column(name = "openingdetails" )
    private String openingdetails;

    @Lob
    @Column(name = "photo", columnDefinition = "MEDIUMBLOB")
    private byte[] photo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

  
}
