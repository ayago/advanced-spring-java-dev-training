package com.ourecommerce.productmanagement.app.controller;

import com.ourecommerce.productmanagement.api.BlacklistProductRequest;
import com.ourecommerce.productmanagement.app.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blacklisted-products")
public class BlacklistedProductController{
    
    private final ProductService productService;
    
    public BlacklistedProductController(ProductService productService){
        this.productService = productService;
    }
    
    @PostMapping
    public void newBlacklistedProduct(@RequestBody BlacklistProductRequest request){
        productService.blacklistProduct(request.getProductId());
    }
}
