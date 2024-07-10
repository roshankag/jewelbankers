package com.balaji.springjwt.dto;

import lombok.Data;

@Data
public class JwtLogin {

    private String email;

    private String password;
}
