package com.ourecommerce.inventorymanagement.app;

import com.ourecommerce.inventorymanagement.api.ProductReservationRequest;
import com.ourecommerce.inventorymanagement.app.service.ProductInventoryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class InventoryManagementApplication {
    
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(InventoryManagementApplication.class, args);
        ProductInventoryService service = context.getBean(ProductInventoryService.class);
        service.performReservation(new ProductReservationRequest());
    }
    
}
