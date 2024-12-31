package com.jewelbankers.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "itemtypeid", referencedColumnName = "id")
    private ItemType itemType;
    
    // Optional if one-to-one relationship is needed for reverse side
    @JsonBackReference
    @OneToOne(mappedBy = "item")
    private PurchaseItems purchaseItem; // Single purchase item per item

	@Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "purity")
    private BigDecimal purity;

    @Column(name = "quantity")
    private Integer quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public BigDecimal getPurity() {
		return purity;
	}

	public void setPurity(BigDecimal purity) {
		this.purity = purity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

    public PurchaseItems getPurchaseItem() {
		return purchaseItem;
	}

	public void setPurchaseItem(PurchaseItems purchaseItem) {
		this.purchaseItem = purchaseItem;
	}
}
