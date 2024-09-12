# Service-to-Service Communication Flow

Here's a detailed flow of how Spring Cloud Security works in service-to-service communication:

1. **Client Service Requests Token**: The client service (e.g., an order service) needs to communicate with another service (e.g., a product service). It first obtains an OAuth 2.0 token from an authorization server.

2. **Token Acquisition**: The client service authenticates itself with the authorization server, typically using its client credentials, and requests an access token. This token represents the client service's identity and permissions.

3. **Request with Token**: The client service includes the obtained access token in the Authorization header of its HTTP requests to the target service.

4. **Target Service Validates Token**: The target service (e.g., the product service) receives the request with the access token and validates it using an OAuth 2.0 resource server configuration.

5. **Access Control**: If the token is valid and the client service has the necessary permissions, the target service processes the request and returns the response.

6. **Error Handling**: If the token is invalid or expired, the target service responds with an appropriate error message, typically a 401 Unauthorized response.

# Example with Maven Dependencies and Import Statements

Here's an example of how you can set up Spring Cloud Security for service-to-service communication:

## Maven Dependencies

```xml
<dependencies>
    <!-- Spring Boot Starter for Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Cloud Starter for Security -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-security</artifactId>
    </dependency>

    <!-- Spring Security OAuth2 Resource Server -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>

    <!-- Spring Security OAuth2 Client -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
    
    <!-- Spring Cloud Starter for OpenFeign (for service-to-service communication) -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    
    <!-- Spring Cloud Dependencies -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>2023.0.3</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
</dependencies>
```

## Configuration

**1. Application Configuration (application.yml)**

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          service-client:
            client-id: your-client-id
            client-secret: your-client-secret
            authorization-grant-type: client_credentials
            token-uri: http://auth-server/oauth/token
        provider:
          auth-server:
            token-uri: http://auth-server/oauth/token
      resourceserver:
        jwt:
          issuer-uri: http://auth-server/oauth/token
```

**2. Feign Client Configuration**

```java
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @GetMapping("/products")
    List<Product> getProducts();
}
```

**3. Security Configuration**

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/products/**").authenticated()
                .anyRequest().permitAll()
            .and()
            .oauth2ResourceServer()
                .jwt();
    }
}
```

# Flow Summary

1. **Order Service** requests a token from the authorization server.
2. **Order Service** uses the token to make a request to the **Product Service**.
3. **Product Service** validates the token and processes the request if valid.
4. **Product Service** responds with the requested data.