package com.ourecommerce.productmanagement.app.controller;

import com.ourecommerce.productmanagement.api.AddNewProductResponse;
import com.ourecommerce.productmanagement.api.ProductDetailsRequest;
import com.ourecommerce.productmanagement.app.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController{
    
    private final ProductService productService;
    
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    
    @PostMapping
    public ResponseEntity<AddNewProductResponse> registerNewProduct(
        @RequestBody ProductDetailsRequest newProductRequest){
        AddNewProductResponse response = productService.registerNewProduct(newProductRequest);
        return ResponseEntity.ok(response);
    }
}
