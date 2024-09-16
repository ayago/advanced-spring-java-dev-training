package com.ourecommerce.webapps.jwtmvc.security;

import com.ourecommerce.webapps.jwtmvc.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{
    
    private final UserDetailsService userDetailsService;
    
    public JwtTokenFilter(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException{
        final Optional<String> authorizationHeader = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));
        if(authorizationHeader.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }
        
        final String jwtToken = authorizationHeader.get();
        final var username = Optional.ofNullable(JwtUtil.extractUsername(jwtToken));
        
        if(username.isEmpty() || SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request, response);
            return;
        }
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(username.get());
        if(!JwtUtil.isTokenValid(jwtToken, userDetails.getUsername())){
            filterChain.doFilter(request, response);
            return;
        }
        
        var authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
