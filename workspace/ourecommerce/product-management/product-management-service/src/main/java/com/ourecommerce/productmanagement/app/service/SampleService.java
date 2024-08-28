package com.ourecommerce.productmanagement.app.service;

import com.ourecommerce.inventorymanagement.api.ProductReservationRequest;
import com.ourecommerce.inventorymanagement.client.InventoryManagementClient;
import org.springframework.stereotype.Service;

@Service
public class SampleService{
    
    private final InventoryManagementClient inventoryManagementClient;
    
    public SampleService(InventoryManagementClient inventoryManagementClient){
        this.inventoryManagementClient = inventoryManagementClient;
    }
    
    public void onboardProduct(){
        System.out.println("Onboarding");
        inventoryManagementClient.performReservation(new ProductReservationRequest());
    }
}
