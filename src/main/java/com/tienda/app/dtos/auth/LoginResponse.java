package com.tienda.app.dtos.auth;

import com.tienda.app.enums.RoleName;

public class LoginResponse {
    private String token;
    private String username;
    private RoleName role;

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }



    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }
}
