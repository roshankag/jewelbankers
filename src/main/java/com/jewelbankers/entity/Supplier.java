package com.jewelbankers.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.jewelbankers.enums.BalanceType;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_name", nullable = false, length = 255)
    private String supplierName;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "gst_no", length = 50)
    private String gstNo;

    @Column(name = "mobile", length = 15)
    private String mobile;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "adhar_card_no", length = 20)
    private String adharCardNo;

    @Column(name = "pan_card_no", length = 20)
    private String panCardNo;

    @Column(name = "selected_item", length = 100)
    private String selectedItem;

    @Column(name = "registration_number", length = 50)
    private String registrationNumber;

    @Column(name = "opening_balance")
    private BigDecimal openingBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "opening_balance_type", length = 10)
    private BalanceType openingBalanceType;

    @Column(name = "opening_details", columnDefinition = "TEXT")
    private String openingDetails;

    @Lob
    @Column(name = "photo", columnDefinition = "MEDIUMBLOB")
    private byte[] photo;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierWeight> weights;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
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

	public String getAdharCardNo() {
		return adharCardNo;
	}

	public void setAdharCardNo(String adharCardNo) {
		this.adharCardNo = adharCardNo;
	}

	public String getPanCardNo() {
		return panCardNo;
	}

	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}

	public String getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	public BalanceType getOpeningBalanceType() {
		return openingBalanceType;
	}

	public void setOpeningBalanceType(BalanceType openingBalanceType) {
		this.openingBalanceType = openingBalanceType;
	}

	public String getOpeningDetails() {
		return openingDetails;
	}

	public void setOpeningDetails(String openingDetails) {
		this.openingDetails = openingDetails;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public List<SupplierWeight> getWeights() {
		return weights;
	}

	public void setWeights(List<SupplierWeight> weights) {
		this.weights = weights;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDate.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

    // Getters and setters
    // Add constructors, if needed
    // Add toString() for debugging purposes
}
