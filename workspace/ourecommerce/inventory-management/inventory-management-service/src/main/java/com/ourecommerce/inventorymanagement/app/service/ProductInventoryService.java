package com.ourecommerce.inventorymanagement.app.service;

import com.ourecommerce.inventorymanagement.api.ProductReservationRequest;
import com.ourecommerce.inventorymanagement.api.ProductReservationResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductInventoryService{
    public ProductReservationResponse performReservation(ProductReservationRequest re){
        System.out.println("Requester "+re);
        return new ProductReservationResponse();
    }
}
