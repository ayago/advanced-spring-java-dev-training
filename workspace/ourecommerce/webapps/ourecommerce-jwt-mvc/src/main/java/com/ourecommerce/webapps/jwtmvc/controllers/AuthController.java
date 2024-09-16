package com.ourecommerce.webapps.jwtmvc.controllers;

import com.ourecommerce.webapps.jwtmvc.dto.AuthRequest;
import com.ourecommerce.webapps.jwtmvc.dto.AuthResponse;
import com.ourecommerce.webapps.jwtmvc.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController{
    
    private final AuthenticationManager authenticationManager;
    
    public AuthController(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            
            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();

            return ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,
                    JwtUtil.generateToken(userDetails.getUsername())
                )
                .body(new AuthResponse().setRole(resolveRole(userDetails)));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }
    
    private String resolveRole(UserDetails userDetails){
        return userDetails.getAuthorities().stream()
            .findFirst()
            .map(GrantedAuthority::getAuthority)
            .map(authority -> authority.replaceAll("ROLE_", ""))
            .orElseThrow();
    }
}
