package com.ourecommerce.inventorymanagement.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

// Your InventoryServiceClient definition

@Configuration
public class InventoryManagementClientAutoconfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public InventoryManagementClient inventoryServiceClient() {
        return new InventoryManagementClient();
    }
}

