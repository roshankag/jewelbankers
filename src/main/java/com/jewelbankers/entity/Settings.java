package com.jewelbankers.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "parameters")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARAM_SEQ")
    private Long paramSeq;

    @Column(name = "PARAM_ID")
    private String paramId;

    @Column(name = "PARAM_VALUE")
    private String paramValue;

    @Column(name = "PARAM_EXAMPLE")
    private String paramExample;

    // Getters and Setters
    public Long getParamSeq() {
        return paramSeq;
    }

    public void setParamSeq(Long paramSeq) {
        this.paramSeq = paramSeq;
    }

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamExample() {
        return paramExample;
    }

    public void setParamExample(String paramExample) {
        this.paramExample = paramExample;
    }

	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
}

