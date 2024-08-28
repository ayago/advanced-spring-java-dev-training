package com.ourecommerce.productmanagement.app;

import com.ourecommerce.productmanagement.app.service.SampleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProductManagementApplication {
    
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ProductManagementApplication.class, args);
        SampleService sampleService = run.getBean(SampleService.class);
        sampleService.onboardProduct();
    }
    
}