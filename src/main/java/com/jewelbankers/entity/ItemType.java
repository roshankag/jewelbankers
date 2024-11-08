package com.jewelbankers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_type")
public class ItemType {
	
	@Id
    @Column(name = "itemtypeno")
    private Integer itemtypeno;     

    @Column(name = "itemtypecode")
    private String itemtypecode; 

    @Column(name = "itemtypedescription")
    private String itemtypedescription;

	
	public Integer getItemtypeno() {
		return itemtypeno;
	}

	public void setItemtypeno(Integer itemtypeno) {
		this.itemtypeno = itemtypeno;
	}

	public String getItemtypecode() {
		return itemtypecode;
	}

	public void setItemtypecode(String itemtypecode) {
		this.itemtypecode = itemtypecode;
	}

	public String getItemtypedescription() {
		return itemtypedescription;
	}

	public void setItemtypedescription(String itemtypedescription) {
		this.itemtypedescription = itemtypedescription;
	}

	

}
