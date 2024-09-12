package com.ourecommerce.ordermanagement.app.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.spring6.circuitbreaker.configure.CircuitBreakerConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CircuitBreakerConfiguration {
    
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer(
        CircuitBreakerConfigurationProperties circuitBreakerConfigurationProperties) {
        
        return factory -> {
            // Load configurations from application.yml
            circuitBreakerConfigurationProperties.getInstances().forEach((name, configProps) -> {
                CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                    .slidingWindowSize(Objects.requireNonNullElse(configProps.getSlidingWindowSize(), 10))
                    .failureRateThreshold(Objects.requireNonNullElse(configProps.getFailureRateThreshold(), 50.0f))
                    .waitDurationInOpenState(configProps.getWaitDurationInOpenState())
                    .permittedNumberOfCallsInHalfOpenState(Objects.requireNonNullElse(configProps.getPermittedNumberOfCallsInHalfOpenState(), 3))
                    .slidingWindowType(Objects.requireNonNullElse(configProps.getSlidingWindowType(), CircuitBreakerConfig.SlidingWindowType.COUNT_BASED))
                    .build();
                
                // Register circuit breaker configurations for each instance
                factory.configure(builder -> builder.circuitBreakerConfig(circuitBreakerConfig), name);
            });
        };
    }
}

