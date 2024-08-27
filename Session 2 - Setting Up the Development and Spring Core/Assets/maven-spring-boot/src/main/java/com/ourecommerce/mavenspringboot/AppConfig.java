package com.ourecommerce.mavenspringboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AppConfig {
    
    @Bean(initMethod = "customInit", destroyMethod = "customDestroy")
    public InventoryService inventoryService() {
        return new InventoryService();
    }
}
