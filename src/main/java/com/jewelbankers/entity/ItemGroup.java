package com.jewelbankers.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "item_groups")
public class ItemGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemid;

    @Column(name = "group_name", unique = true, nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "itemGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemMaster> itemMaster;

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<ItemMaster> getItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(List<ItemMaster> itemMaster) {
		this.itemMaster = itemMaster;
	}

    // Getters, Setters, Constructors
}
