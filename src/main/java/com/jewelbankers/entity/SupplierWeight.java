package com.jewelbankers.entity;

import java.math.BigDecimal;

import com.jewelbankers.enums.CreditDebit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "supplier_weights")
public class SupplierWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "item_type_no", nullable = false)
    private ItemType itemType; // Changed from String to ItemType reference

    @Column(name = "weight")
    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_debit", length = 10)
    private CreditDebit creditDebit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}


	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public CreditDebit getCreditDebit() {
		return creditDebit;
	}

	public void setCreditDebit(CreditDebit creditDebit) {
		this.creditDebit = creditDebit;
	}
	
	@Override
    public String toString() {
        return "SupplierWeight{" +
                "id=" + id +
                ", supplier=" + supplier +
                ", itemType=" + itemType +
                ", weight=" + weight +
                ", creditDebit=" + creditDebit +
                '}';
    }
}
