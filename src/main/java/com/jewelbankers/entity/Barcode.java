package com.jewelbankers.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "barcodes")
public class Barcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag")
    private String tag;
    
    @ManyToOne
    @JoinColumn(name = "itemtypeno", nullable = false)
    private ItemType itemType;

    @Column(name = "gross_weight")
    private BigDecimal grossWeight;

    @Column(name = "net_weight")
    private BigDecimal netWeight;

    @Column(name = "purity")
    private BigDecimal purity;

    @Column(name = "black_beads_weight")
    private BigDecimal blackBeadsWeight;

    @Column(name = "stone_weight")
    private BigDecimal stoneWeight;

    @Column(name = "making_charge_per_gram")
    private BigDecimal makingChargePerGram;

    @Column(name = "making_percentage")
    private Double makingPercentage;

    @Column(name = "size")
    private String size;

    @Column(name = "hallmark_charges")
    private Double hallmarkCharges;

    @Column(name = "huid")
    private String huid;

    @Column(name = "barcode_created_date")
    private LocalDate barcodeCreatedDate;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	public BigDecimal getPurity() {
		return purity;
	}

	public void setPurity(BigDecimal purity) {
		this.purity = purity;
	}

	public BigDecimal getBlackBeadsWeight() {
		return blackBeadsWeight;
	}

	public void setBlackBeadsWeight(BigDecimal blackBeadsWeight) {
		this.blackBeadsWeight = blackBeadsWeight;
	}

	public BigDecimal getStoneWeight() {
		return stoneWeight;
	}

	public void setStoneWeight(BigDecimal stoneWeight) {
		this.stoneWeight = stoneWeight;
	}

	public BigDecimal getMakingChargePerGram() {
		return makingChargePerGram;
	}

	public void setMakingChargePerGram(BigDecimal makingChargePerGram) {
		this.makingChargePerGram = makingChargePerGram;
	}

	public Double getMakingPercentage() {
		return makingPercentage;
	}

	public void setMakingPercentage(Double makingPercentage) {
		this.makingPercentage = makingPercentage;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Double getHallmarkCharges() {
		return hallmarkCharges;
	}

	public void setHallmarkCharges(Double hallmarkCharges) {
		this.hallmarkCharges = hallmarkCharges;
	}

	public String getHuid() {
		return huid;
	}

	public void setHuid(String huid) {
		this.huid = huid;
	}

	public LocalDate getBarcodeCreatedDate() {
		return barcodeCreatedDate;
	}

	public void setBarcodeCreatedDate(LocalDate barcodeCreatedDate) {
		this.barcodeCreatedDate = barcodeCreatedDate;
	}

	public LocalDate getBarcodeExpiryDate() {
		return barcodeExpiryDate;
	}

	public void setBarcodeExpiryDate(LocalDate barcodeExpiryDate) {
		this.barcodeExpiryDate = barcodeExpiryDate;
	}

	@Column(name = "barcode_expiry_date")
    private LocalDate barcodeExpiryDate;

    // Getters, Setters, Constructors
}
