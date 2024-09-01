package com.balaji.springjwt.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "pledge")
public class Pledge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bill_serial", length = 2, nullable = false, updatable = false)
    private String billSerial;

    @Column(name = "bill_no", nullable = false)
    private int billNo;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "product_type", nullable = false)
    private String productType;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "rate_of_interest", nullable = false)
    private double rateOfInterest;

    @Column(name = "present_value", nullable = false)
    private double presentValue;

    @Column(name = "bill_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date billDate;

    @Column(name = "grams", nullable = false)
    private double grams;

    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

    @Column(name = "product_description", nullable = false)
    private String productDescription;

    @Column(name = "amount_in_words", nullable = false)
    private String amountInWords;

    @Column(name = "interest_pledge", nullable = false)
    private double interestPledge;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @JsonManagedReference
    @OneToOne(mappedBy = "pledge", cascade = CascadeType.ALL)
    private BillUpload billUpload;

    public BillUpload getBillUpload() {
        return billUpload;
    }

    public void setBillUpload(BillUpload billUpload) {
        this.billUpload = billUpload;
    }

    @PrePersist
    protected void onCreate() {
        this.billNo = generateUniqueBillNo();
    }

    private int generateUniqueBillNo() {
        Random random = new Random();
        int billNo;

        do {
            billNo = random.nextInt(1000000);
        } while (billNoExists(billNo));

        return billNo;
    }

    private boolean billNoExists(int billNo) {
        // Assuming you have a method in your service/repository to check for existing bill numbers
        // return pledgeRepository.existsByBillNo(billNo);
        return false; // Replace this with actual logic
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getBillSerial() {
        return billSerial;
    }
    
    public void setBillSerial(String billSerial) {
        this.billSerial = billSerial;
    }
    
    public int getBillNo() {
        return billNo;
    }
    
    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String getProductType() {
        return productType;
    }
    
    public void setProductType(String productType) {
        this.productType = productType;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public double getRateOfInterest() {
        return rateOfInterest;
    }
    
    public void setRateOfInterest(double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }
    
    public double getPresentValue() {
        return presentValue;
    }
    
    public void setPresentValue(double presentValue) {
        this.presentValue = presentValue;
    }
    
    public Date getBillDate() {
        return billDate;
    }
    
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }
    
    public double getGrams() {
        return grams;
    }
    
    public void setGrams(double grams) {
        this.grams = grams;
    }
    
    public int getProductQuantity() {
        return productQuantity;
    }
    
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
    
    public String getProductDescription() {
        return productDescription;
    }
    
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    
    public String getAmountInWords() {
        return amountInWords;
    }
    
    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }
    
    public double getInterestPledge() {
        return interestPledge;
    }
    
    public void setInterestPledge(double interestPledge) {
        this.interestPledge = interestPledge;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    
}