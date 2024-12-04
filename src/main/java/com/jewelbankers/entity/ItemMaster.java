package com.jewelbankers.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "item_master")
public class ItemMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "item_type_no", nullable = false)
    private ItemType itemType;  // ItemType entity is already defined

    @Column(name = "purity", length = 50)
    private String purity;

    @Column(name = "weight")
    private  BigDecimal weight;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getPurity() {
        return purity;
    }

    public void setPurity(String purity) {
        this.purity = purity;
    }

    public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	@Override
    public String toString() {
        return "ItemMaster{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemType=" + itemType +
                ", purity='" + purity + '\'' +
                ", weight=" + weight +
                '}';
    }
}
