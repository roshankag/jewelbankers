package com.jewelbankers.entity;

import java.math.BigDecimal;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@EntityScan
@Entity
@Table(name = "bill_header")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billSequence;

    @Column(name = "billSerial")
    private Character billSerial;

    @Column(name = "billNo")
    private Integer billNo;

    @Column(name = "billDate")
    private String billDate;
    
    @Column(name = "customerId")
    private Integer customerId;

    public Integer getBillSequence() {
		return billSequence;
	}

	public void setBillSequence(int billSequence) {
		this.billSequence = billSequence;
	}

	public Character getBillSerial() {
		return billSerial;
	}

	public void setBillSerial(char billSerial) {
		this.billSerial = billSerial;
	}

	public Integer getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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

	public String getRedemptionDate() {
		return redemptionDate;
	}

	public void setRedemptionDate(String redemptionDate) {
		this.redemptionDate = redemptionDate;
	}

	public BigDecimal getRedemptionInterest() {
		return redemptionInterest;
	}

	public void setRedemptionInterest(BigDecimal redemptionInterest) {
		this.redemptionInterest = redemptionInterest;
	}

	public BigDecimal getRedemptionTotal() {
		return redemptionTotal;
	}

	public void setRedemptionTotal(BigDecimal redemptionTotal) {
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

	public void setBillRedemNo(int billRedemNo) {
		this.billRedemNo = billRedemNo;
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

    @Column(name = "rateOfInterst")
    private BigDecimal rateOfInterest;
    
    @Column(name = "amount")
    private Integer amount;

    @Column(name = "amountInwords")
    private String amountInWords;
     
    @Column(name = "presentValue")
    private Integer presentValue;

    @Column(name = "grams")
    private BigDecimal grams;

    @Column(name = "monthlyIncome")
    private Integer monthlyIncome;

    @Column(name = "redemptionDate")
    private String redemptionDate;

    @Column(name = "redemptionInterest")
    private BigDecimal redemptionInterest;

    @Column(name = "redemptionTotal")
    private BigDecimal redemptionTotal;

    @Column(name = "redemptionStatus")
    private Character redemptionStatus;

    @Column(name = "billRedemSerial")
    private Character billRedemSerial;

    @Column(name = "billRedemNo")
    private Integer billRedemNo;

    @Column(name = "comments")
    private String comments;
}
