# In-Memory Authentication with Spring Security

In-memory authentication is a simple method to secure an application using Spring Security. It allows you to define a list of users, passwords, and roles directly in the application configuration. This is suitable for development, testing, or small-scale applications where user management does not need to be dynamic.

## How It Works

1. **Configuration Class**: The `@Configuration` class is annotated with `@EnableWebSecurity` to enable Spring Security's web security support and provide the Spring MVC integration.
2. **Authentication Manager**: The `AuthenticationManager` is customized by overriding the `configure(AuthenticationManagerBuilder auth)` method, where users are defined with passwords and roles.
3. **Password Encoding**: A `PasswordEncoder` is defined to encode the passwords in a secure manner.
4. **HTTP Security Configuration**: The `configure(HttpSecurity http)` method is overridden to define URL-based security.

## Complete Sample Code

Here is the complete code for implementing in-memory authentication in a Spring Boot application using version 3.3.3.

### 1. `pom.xml` Maven Dependencies

Ensure that the following dependencies are added to your `pom.xml`:

```xml
<dependencies>
    <!-- Spring Boot Starter for Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Boot Starter for Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter for Thymeleaf (Optional for UI) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
</dependencies>
```

### 2. Security Configuration Class

Create a configuration class `SecurityConfig.java` in the `src/main/java` directory.

```java
package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
            .antMatchers("/").permitAll()
            .and()
            .formLogin();
    }
}
```

### 3. Application Class

Ensure that you have a Spring Boot application class (`Application.java`) to start your application.

```java
package com.example.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## Explanation of the Code

- **UserDetailsService**: The `userDetailsService` bean defines two users with their usernames, encoded passwords, and roles.
- **PasswordEncoder**: A `BCryptPasswordEncoder` is used for encoding passwords.
- **HttpSecurity Configuration**: The `configure(HttpSecurity http)` method restricts access based on roles:
  - URLs starting with `/admin/` are accessible only by users with the `ADMIN` role.
  - URLs starting with `/user/` are accessible by users with either the `USER` or `ADMIN` role.
  - All other URLs are accessible without authentication.

## Running the Application

1. **Start the Application**: Run the `Application` class as a Java application.
2. **Access the Application**: Open your browser and go to `http://localhost:8080/`.
3. **Login**: Try accessing different URLs (e.g., `/admin`, `/user`) and log in with the credentials:
   - Username: `user`, Password: `password`
   - Username: `admin`, Password: `admin`