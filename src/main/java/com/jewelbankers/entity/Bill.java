package com.jewelbankers.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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

//@EntityScan
@Entity
@Table(name = "bill_header")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billSequence") 
    private Integer billSequence;
	/*
	 * private Integer productNo;
	 * 
	 * @Column(name = "productDescription") private String productDescription;
	 * 
	 * @Column(name = "productQuantity") private Integer productQuantity;
	 */

    public Integer getBillSequence() {
		return billSequence;
	}

	public void setBillSequence(Integer billSequence) {
		this.billSequence = billSequence;
	}

	@Column(name = "billSerial")
    private Character billSerial;

    @Column(name = "billNo")
    private Integer billNo;

    @Column(name = "billDate")
    private LocalDate billDate;
    
	public List<BillDetail> getBillDetails() {
		return billDetails;
	}

	public void setBillDetails(List<BillDetail> billDetails) {
		this.billDetails = billDetails;
	}

	@OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
    private List<BillDetail> billDetails;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;

	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setBillSerial(Character billSerial) {
		this.billSerial = billSerial;
	}

	public void setBillNo(Integer billNo) {
		this.billNo = billNo;
	}

	public void setProductTypeNo(Integer productTypeNo) {
		this.productTypeNo = productTypeNo;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setPresentValue(Integer presentValue) {
		this.presentValue = presentValue;
	}

	public void setMonthlyIncome(Integer monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public void setRedemptionStatus(Character redemptionStatus) {
		this.redemptionStatus = redemptionStatus;
	}

	public void setBillRedemSerial(Character billRedemSerial) {
		this.billRedemSerial = billRedemSerial;
	}

	public void setBillRedemNo(Integer billRedemNo) {
		this.billRedemNo = billRedemNo;
	}

	/*
	 * public String getCustomerId() { return CustomerId; }
	 * 
	 * public void setCustomerId(String customerId) { CustomerId = customerId; }
	
	
	/*
	 * public Integer getProductNo() { return productNo; }
	 * 
	 * public void setProductNo(Integer productNo) { this.productNo = productNo; }
	 * 
	 * public String getProductDescription() { return productDescription; }
	 */

//	public Customer getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}

	/*
	 * public void setProductDescription(String productDescription) {
	 * this.productDescription = productDescription; }
	 * 
	 * public Integer getProductQuantity() { return productQuantity; }
	 * 
	 * public void setProductQuantity(Integer productQuantity) {
	 * this.productQuantity = productQuantity; }
	 */

	public Character getBillSerial() {
		return billSerial;
	}

	public void setBillSerial(char billSerial) {
		this.billSerial = billSerial;
	}

	public Integer getBillNo() {
		return billNo;
	}

	public LocalDate getBillDate() {
		return billDate;
	}

	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}

	public String getCareOf() {
		return careOf;
	}

	public void setCareOf(String careOf) {
		this.careOf = careOf;
	}

	public Integer getProductTypeNo() {
		return productTypeNo;
	}

	public void setProductTypeNo(int productTypeNo) {
		this.productTypeNo = productTypeNo;
	}

	public BigDecimal getRateOfInterest() {
		return rateOfInterest;
	}

	public void setRateOfInterest(BigDecimal rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public Integer getPresentValue() {
		return presentValue;
	}

	public void setPresentValue(int presentValue) {
		this.presentValue = presentValue;
	}

	public BigDecimal getGrams() {
		return grams;
	}

	public void setGrams(BigDecimal grams) {
		this.grams = grams;
	}

	public Integer getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(int monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public LocalDate getRedemptionDate() {
		return redemptionDate;
	}

	public void setRedemptionDate(LocalDate redemptionDate) {
		this.redemptionDate = redemptionDate;
	}

	public Double getRedemptionInterest() {
		return redemptionInterest;
	}

	public void setRedemptionInterest(Double redemptionInterest) {
		this.redemptionInterest = redemptionInterest;
	}

	public Double getRedemptionTotal() {
		return redemptionTotal;
	}

	public void setRedemptionTotal(Double redemptionTotal) {
		this.redemptionTotal = redemptionTotal;
	}

	public Character getRedemptionStatus() {
		return redemptionStatus;
	}

	public void setRedemptionStatus(char redemptionStatus) {
		this.redemptionStatus = redemptionStatus;
	}

	public Character getBillRedemSerial() {
		return billRedemSerial;
	}

	public void setBillRedemSerial(char billRedemSerial) {
		this.billRedemSerial = billRedemSerial;
	}

	public Integer getBillRedemNo() {
		return billRedemNo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "careOf")
    private String careOf;
    
    @Column(name = "productTypeNo")
    private Integer productTypeNo;

    @Column(name = "rateOfInterest")
    private BigDecimal rateOfInterest;
    
    @Column(name = "amount")
    private Integer amount;

    @Column(name = "amountInWords")
    private String amountInWords;
     
    @Column(name = "presentValue")
    private Integer presentValue;

    @Column(name = "grams")
    private BigDecimal grams;

    @Column(name = "monthlyIncome")
    private Integer monthlyIncome;

    @Column(name = "redemptionDate")
    private LocalDate redemptionDate;

    @Column(name = "redemptionInterest")
    private Double redemptionInterest;

    @Column(name = "redemptionTotal")
    private Double redemptionTotal;

    @Column(name = "redemptionStatus")
    private Character redemptionStatus;

    @Column(name = "billRedemSerial")
    private Character billRedemSerial;

    @Column(name = "billRedemNo")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billRedemNo;

    @Column(name = "comments")
    private String comments;
    
    private Long productSeq;

	public Long getProductSeq() {
		return productSeq;
	}

	public void setProductSeq(Long productSeq) {
		this.productSeq = productSeq;
	}	
}
