package com.ourecommerce.inventorymanagement.app.controllers;

import com.ourecommerce.inventorymanagement.api.ProductInventoryResponse;
import com.ourecommerce.inventorymanagement.app.service.ProductInventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product-inventory")
public class InventoryController{
    
    private final ProductInventoryService productInventoryService;
    
    public InventoryController(ProductInventoryService productInventoryService){
        this.productInventoryService = productInventoryService;
    }
    
    @GetMapping("/{productCode}")
    public Mono<ProductInventoryResponse> getInventoryForProductWithCode(@PathVariable("productCode") String productCode){
        return productInventoryService.getInventoryForProductWithCode(productCode);
    }
}
