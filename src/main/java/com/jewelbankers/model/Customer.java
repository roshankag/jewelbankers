package com.jewelbankers.model;

//import org.springframework.aot.generate.Generated;
//import org.springframework.boot.autoconfigure.domain.EntityScan;

//@EntityScan
public class Customer {
    
    //@Id
    //@Generated(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private String email;
    private String phone;

    // Constructors
    public Customer() {}

    public Customer(Long id,String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.id = id;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}