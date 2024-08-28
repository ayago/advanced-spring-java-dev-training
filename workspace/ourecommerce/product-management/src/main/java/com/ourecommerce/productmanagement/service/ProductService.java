package com.ourecommerce.productmanagement.service;

import com.ourecommerce.inventorymanagement.api.ProductReservationRequest;
import com.ourecommerce.inventorymanagement.client.InventoryManagementClient;
import com.ourecommerce.productmanagement.api.OnboardRequest;
import com.ourecommerce.productmanagement.data.OperationResult;
import org.springframework.stereotype.Service;

@Service
public class ProductService{
    
    private final InventoryManagementClient inventoryManagementClient;
    
    public ProductService(InventoryManagementClient inventoryManagementClient){
        this.inventoryManagementClient = inventoryManagementClient;
    }
    
    public OperationResult onboardProduct(OnboardRequest onboardRequest){
        System.out.println("Onboarding with request "+onboardRequest);
        inventoryManagementClient.performReservation(new ProductReservationRequest());
        return new OperationResult();
    }
}
