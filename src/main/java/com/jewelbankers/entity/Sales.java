package com.jewelbankers.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToOne(fetch = FetchType.EAGER,  cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "customerid", referencedColumnName = "customerid")
    private Customer customer;
    
    
    @JsonManagedReference("sales-items")
    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
    private List<SalesItems> salesItems = new ArrayList<>();

    @Column(name = "billno")
    private Long billno;
    
    @Column(name = "billprefix")
    private String billprefix;
    
    @Column(name = "billdate")
    private LocalDateTime billdate;
    
    @Column(name = "paymenttype")
    private String paymenttype;
    
    @Column(name = "salesman")
    private String salesman;
    
    @Column(name = "billduedatetime")
    private LocalDateTime billduedatetime;

    @Column(name = "billamount")
    private BigDecimal billamount;
    
    @Column(name = "gstamount")
    private double gstamount;
    
    @Column(name = "billdiscount")
    private double billdiscount;
    
    @Column(name = "totalamount")
    private double totalamount;
    
    @Column(name = "paymentsubtype")
    private String paymentsubtype;
    
    @Column(name = "paymentrefno")
    private String paymentrefno;
    
    @Column(name = "paymentrecieved")
    private BigDecimal paytmentrecieved;
    
    @Column(name = "paymentbalanceamt")
    private BigDecimal paymentbalanceamt;
    
    @Column(name = "paymentseqno")
    private Long paymentseqno;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<SalesItems> getSalesItems() {
		return salesItems;
	}

	public void setSalesItems(List<SalesItems> salesItems) {
		this.salesItems = salesItems;
	}

	public Long getBillno() {
		return billno;
	}

	public void setBillno(Long billno) {
		this.billno = billno;
	}

	public String getBillprefix() {
		return billprefix;
	}

	public void setBillprefix(String billprefix) {
		this.billprefix = billprefix;
	}

	public LocalDateTime getBilldate() {
		return billdate;
	}

	public void setBilldate(LocalDateTime billdate) {
		this.billdate = billdate;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public LocalDateTime getBillduedatetime() {
		return billduedatetime;
	}

	public void setBillduedatetime(LocalDateTime billduedatetime) {
		this.billduedatetime = billduedatetime;
	}

	public BigDecimal getBillamount() {
		return billamount;
	}

	public void setBillamount(BigDecimal billamount) {
		this.billamount = billamount;
	}

	public double getGstamount() {
		return gstamount;
	}

	public void setGstamount(double gstamount) {
		this.gstamount = gstamount;
	}

	public double getBilldiscount() {
		return billdiscount;
	}

	public void setBilldiscount(double billdiscount) {
		this.billdiscount = billdiscount;
	}

	public double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}

	public String getPaymentsubtype() {
		return paymentsubtype;
	}

	public void setPaymentsubtype(String paymentsubtype) {
		this.paymentsubtype = paymentsubtype;
	}

	public String getPaymentrefno() {
		return paymentrefno;
	}

	public void setPaymentrefno(String paymentrefno) {
		this.paymentrefno = paymentrefno;
	}

	public BigDecimal getPaytmentrecieved() {
		return paytmentrecieved;
	}

	public void setPaytmentrecieved(BigDecimal paytmentrecieved) {
		this.paytmentrecieved = paytmentrecieved;
	}

	public BigDecimal getPaymentbalanceamt() {
		return paymentbalanceamt;
	}

	public void setPaymentbalanceamt(BigDecimal paymentbalanceamt) {
		this.paymentbalanceamt = paymentbalanceamt;
	}

	public Long getPaymentseqno() {
		return paymentseqno;
	}

	public void setPaymentseqno(Long paymentseqno) {
		this.paymentseqno = paymentseqno;
	}
    
}
