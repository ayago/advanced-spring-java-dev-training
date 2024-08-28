package com.ourecommerce.productmanagement;

import com.ourecommerce.productmanagement.api.OnboardRequest;
import com.ourecommerce.productmanagement.service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProductManagementApplication {
    
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ProductManagementApplication.class, args);
        ProductService productService = run.getBean(ProductService.class);
        productService.onboardProduct(new OnboardRequest());
    }
    
}