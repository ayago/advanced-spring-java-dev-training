package com.ourecommerce.webapps.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    @Profile("sec-im")
    public UserDetailsService imUserDetailsService() {
        UserDetails user = User.withUsername("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();
        
        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build();
        
        return new InMemoryUserDetailsManager(user, admin);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Profile("sec-jdbc")
    public UserDetailsService jdbcUserDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
        return userDetailsManager;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                .requestMatchers("/home").authenticated()
                .requestMatchers("/products").hasRole("ADMIN")
                .requestMatchers("/orders").hasRole("USER")
                .requestMatchers("/").permitAll()
                .requestMatchers("/login").permitAll()
            )
            .formLogin(formLoginConfigurer -> formLoginConfigurer
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .logout(LogoutConfigurer::permitAll);
        
        return http.build();
    }
}
