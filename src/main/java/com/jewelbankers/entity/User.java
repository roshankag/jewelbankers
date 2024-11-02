package com.jewelbankers.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;



import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })

public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 50)
  private String username;
  
  @Column(name = "USER_DATABASE_NAME")
  @NotBlank
  @Size(max = 50)
  private String userDatabaseName;
  
  public String getUserDatabaseName() {
	return userDatabaseName;
}

public void setUserDatabaseName(String userDatabaseName) {
	this.userDatabaseName = userDatabaseName;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public String getShopName() {
	return shopName;
}

public void setShopName(String shopName) {
	this.shopName = shopName;
}

public Date getCreateDate() {
	return createDate;
}

public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}

public Date getUpdateDate() {
	return updateDate;
}

public void setUpdateDate(Date updateDate) {
	this.updateDate = updateDate;
}

  @Column(name = "location")
  @NotBlank
  @Size(max = 50)
  private String location;
  
  @Column(name = "SHOP_NAME")
  @NotBlank
  @Size(max = 50)
  private String shopName;
  
  @Column(name = "CREATE_DATE")
  @Temporal(TemporalType.DATE)
  private Date createDate;

  @Column(name = "UPDATE_DATE")
  @Temporal(TemporalType.DATE)
  private Date updateDate;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @Column(name = "reset_password_token")
    private String resetPasswordToken;
  
  

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(  name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
