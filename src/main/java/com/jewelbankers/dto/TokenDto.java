package com.jewelbankers.dto;

public class TokenDto {

    private String g_csrf_token;

    private String credentials;

    public String getToken() {
        return g_csrf_token;
    }
    public String getCredentials() {
        return credentials;
    }
    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
    public void setToken(String token) {
        this.g_csrf_token = token;
    }
}