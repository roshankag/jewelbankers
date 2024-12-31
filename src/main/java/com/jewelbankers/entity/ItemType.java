package com.jewelbankers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_type")
public class ItemType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "description")
	private String description;

	@Column(name = "currentrate")
	private String currentrate;

	@Column(name = "gstpercentage")
	private String gstpercentage;
	
	@Column(name = "gstmakingpercentage")
	private String gstmakingpercentage;

	public String getGstmakingpercentage() {
		return gstmakingpercentage;
	}

	public void setGstmakingpercentage(String gstmakingpercentage) {
		this.gstmakingpercentage = gstmakingpercentage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrentrate() {
		return currentrate;
	}

	public void setCurrentrate(String currentrate) {
		this.currentrate = currentrate;
	}

	public String getGstpercentage() {
		return gstpercentage;
	}

	public void setGstpercentage(String gstpercentage) {
		this.gstpercentage = gstpercentage;
	}
	
}
