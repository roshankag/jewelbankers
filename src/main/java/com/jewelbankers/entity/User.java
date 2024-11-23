package com.jewelbankers.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @Column(name = "reset_password_token")
    private String resetPasswordToken;
  
  // Add the new field below
  @Column(name = "end_date")
  private LocalDate endDate;
  
  @Column(name = "free_trial_start_date")
  private LocalDate freeTrialStartDate;
  
  @Column(name = "free_trial_end_date")
  private LocalDate freeTrialEndDate;
  
  @Column(name = "yearly_subscription_start_date")
  private LocalDate yearlySubscriptionStartDate;
  
  @Column(name = "yearly_subscription_end_date")
  private LocalDate yearlySubscriptionEndDate;

  public LocalDate getFreeTrialStartDate() {
	return freeTrialStartDate;
}

public void setFreeTrialStartDate(LocalDate freeTrialStartDate) {
	this.freeTrialStartDate = freeTrialStartDate;
}

public LocalDate getFreeTrialEndDate() {
	return freeTrialEndDate;
}

public void setFreeTrialEndDate(LocalDate freeTrialEndDate) {
	this.freeTrialEndDate = freeTrialEndDate;
}

public LocalDate getYearlySubscriptionStartDate() {
	return yearlySubscriptionStartDate;
}

public void setYearlySubscriptionStartDate(LocalDate yearlySubscriptionStartDate) {
	this.yearlySubscriptionStartDate = yearlySubscriptionStartDate;
}

public LocalDate getYearlySubscriptionEndDate() {
	return yearlySubscriptionEndDate;
}

public void setYearlySubscriptionEndDate(LocalDate yearlySubscriptionEndDate) {
	this.yearlySubscriptionEndDate = yearlySubscriptionEndDate;
}

public LocalDate getEndDate() {
	return endDate;
}

public void setEndDate(LocalDate endDate) {
	this.endDate = endDate;
}

@NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
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