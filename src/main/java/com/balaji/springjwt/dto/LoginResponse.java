package com.balaji.springjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

	// Constructor 1
	/*
	 * public LoginResponse(String token) { this.token = token; }
	 */

//    // Constructor 2 (this is likely causing the issue)
//    public LoginResponse(String token) {
//        this.token = token;
//    }
    


	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
