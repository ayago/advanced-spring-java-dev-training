# Security with Spring Security: JDBC Authentication

JDBC Authentication allows Spring Security to authenticate users against a database using JDBC. This approach is useful when you want to manage users and roles in a relational database rather than relying on an in-memory or external directory service (like LDAP).

## How JDBC Authentication Works

JDBC Authentication leverages the `UserDetailsService` implementation provided by Spring Security that interacts with a database using JDBC. Spring Boot provides `JdbcUserDetailsManager`, which is a built-in implementation that reads user details (such as username, password, and roles) from a database.

To configure JDBC authentication:

1. **Database Schema**: You need a schema that contains user credentials and authorities (roles).
2. **DataSource Configuration**: A `DataSource` bean must be configured to connect to your database.
3. **Password Encoding**: Passwords stored in the database should be encoded using a secure algorithm (e.g., BCrypt).
4. **Spring Security Configuration**: Configure Spring Security to use JDBC for authentication and to specify the queries used for fetching user details.

## Sample Code for JDBC Authentication

Below is a complete example of setting up JDBC Authentication with Spring Boot 3.3.3.

### 1. Maven Dependencies

Add the following dependencies to your `pom.xml`:

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

    <!-- Spring Boot Starter for JDBC -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>

    <!-- MySQL Driver (or your preferred database driver) -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Password Encoder -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-crypto</artifactId>
    </dependency>
</dependencies>
```

### 2. Database Schema

Create the following tables in your database:

```sql
CREATE TABLE users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);
```

### 3. Spring Boot Application Configuration

Configure the `application.properties` file to set up your DataSource:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=dbuser
spring.datasource.password=dbpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Spring Security JDBC Authentication queries
spring.queries.users-query=SELECT username, password, enabled FROM users WHERE username = ?
spring.queries.roles-query=SELECT username, authority FROM authorities WHERE username = ?
```

### 4. Security Configuration Class

Create a security configuration class to set up JDBC authentication.

```java
package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .formLogin(withDefaults())
            .httpBasic(withDefaults());
        return http.build();
    }
}
```

### 5. Sample User Insertion

Insert sample users into the database:

```sql
INSERT INTO users (username, password, enabled) VALUES ('john', '$2a$10$DowJ4J/utN1kT1yQYv0jOOk4jsGZ0ReRyoaG6p9XEsFsWbw6wLwWm', true); 
INSERT INTO authorities (username, authority) VALUES ('john', 'ROLE_USER');
```

The password (`password123`) is encoded using BCrypt.

### 6. Password Encoding

To encode passwords using BCrypt, use the following utility:

```java
package com.example.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password123";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
    }
}
```