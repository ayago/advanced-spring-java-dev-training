package com.ourecommerce.webapps.jwtmvc.dto;

public class AuthResponse{
    private String role;
    
    public String getRole(){
        return role;
    }
    
    public AuthResponse setRole(String role){
        this.role = role;
        return this;
    }
}
