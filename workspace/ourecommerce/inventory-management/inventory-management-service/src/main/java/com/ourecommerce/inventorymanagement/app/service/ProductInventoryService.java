package com.ourecommerce.inventorymanagement.app.service;

import com.ourecommerce.inventorymanagement.api.ProductInventoryResponse;
import com.ourecommerce.inventorymanagement.api.ProductReservationRequest;
import com.ourecommerce.inventorymanagement.api.ProductReservationResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductInventoryService{
    
    public ProductReservationResponse performReservation(ProductReservationRequest re){
        System.out.println("Requester "+re);
        return new ProductReservationResponse();
    }
    
    public Mono<ProductInventoryResponse> getInventoryForProductWithCode(String productCode){
        ProductInventoryResponse productInventory = new ProductInventoryResponse();
        productInventory.setProductCode(productCode)
            .setAvailableStock(10)
            .setReservedStock(5);
        return Mono.just(productInventory);
    }
}
