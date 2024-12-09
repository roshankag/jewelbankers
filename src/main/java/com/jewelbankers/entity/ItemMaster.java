package com.jewelbankers.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_master")
public class ItemMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;
    
    @ManyToOne
    @JoinColumn(name = "itemid")
    private ItemGroup itemGroup;

    public ItemGroup getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(ItemGroup itemGroup) {
		this.itemGroup = itemGroup;
	}

	@ManyToOne
    @JoinColumn(name = "itemtypeno", nullable = false)
    private ItemType itemtypeno;  // ItemType entity is already defined

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

    public ItemType getItemtypeno() {
		return itemtypeno;
	}

	public void setItemtypeno(ItemType itemtypeno) {
		this.itemtypeno = itemtypeno;
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
                ", itemType=" + itemtypeno +
                ", purity='" + purity + '\'' +
                ", weight=" + weight +
                '}';
    }
}
