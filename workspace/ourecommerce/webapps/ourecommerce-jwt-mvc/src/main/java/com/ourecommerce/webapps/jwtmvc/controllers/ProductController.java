package com.ourecommerce.webapps.jwtmvc.controllers;

import com.ourecommerce.productmanagement.api.AddNewProductResponse;
import com.ourecommerce.productmanagement.api.ProductDetailsRequest;
import com.ourecommerce.productmanagement.api.ProductDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private WebClient webClient;
    
    @GetMapping
    public List<ProductDetailsResponse> getProducts() {
        return webClient.get()
            .uri("/products")
            .retrieve()
            .bodyToFlux(ProductDetailsResponse.class)
            .collectList()
            .block();
    }
    
    @PostMapping
    public AddNewProductResponse createProduct(@RequestBody ProductDetailsRequest product) {
        return webClient.post()
            .uri("/products")
            .bodyValue(product)
            .retrieve()
            .bodyToMono(AddNewProductResponse.class)
            .block();
    }
}

