package com.ourecommerce.inventorymanagement.client;

import com.ourecommerce.inventorymanagement.api.ProductReservationRequest;
import com.ourecommerce.inventorymanagement.api.ProductReservationResponse;

public class InventoryManagementClient{
    public ProductReservationResponse performReservation(ProductReservationRequest re){
        System.out.println("Mock call to Inventory Service "+re);
        return new ProductReservationResponse();
    }
}
