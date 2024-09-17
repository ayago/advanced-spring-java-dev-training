package com.ourecommerce.webapps.mvcoidc.controllers;

import com.ourecommerce.productmanagement.api.AddNewProductResponse;
import com.ourecommerce.productmanagement.api.ProductDetailsRequest;
import com.ourecommerce.productmanagement.api.ProductDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
    
    @Autowired
    private WebClient webClient;
    
    @GetMapping("/products")
    public String getProducts(Model model) {
        webClient.get()
            .uri("/products")
            .retrieve()
            .bodyToFlux(ProductDetailsResponse.class)
            .collectList()
            .doOnNext(products -> model.addAttribute("products", products))
            .block();
        
        return "products";
    }
    
    @PostMapping("/products")
    public ModelAndView createProduct(@ModelAttribute ProductDetailsRequest product, Model model) {
        AddNewProductResponse response = webClient.post()
            .uri("/products")
            .bodyValue(product)
            .retrieve()
            .bodyToMono(AddNewProductResponse.class)
            .block();
        
        model.addAttribute("response", response);
        return new ModelAndView("redirect:/products");
    }
}

