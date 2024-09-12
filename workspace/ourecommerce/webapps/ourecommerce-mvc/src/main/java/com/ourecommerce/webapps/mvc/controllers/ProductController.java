package com.ourecommerce.webapps.mvc.controllers;

import com.ourecommerce.webapps.mvc.dto.Product;
import com.ourecommerce.webapps.mvc.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;

@Controller
public class ProductController {
    
    @Autowired
    private WebClient webClient;
    
    @GetMapping("/products")
    public String getProducts(Model model) {
        Flux<Product> productsFlux = webClient.get()
            .uri("/products")
            .retrieve()
            .bodyToFlux(Product.class);
        
        productsFlux
            .collectList()
            .doOnNext(products -> model.addAttribute("products", products))
            .block();
        
        return "products";
    }
    
    @PostMapping("/products")
    public ModelAndView createProduct(@ModelAttribute Product product, Model model) {
        ProductResponse createProductResponse = webClient.post()
            .uri("/products")
            .bodyValue(product)
            .retrieve()
            .bodyToMono(ProductResponse.class)
            .block();
        
        model.addAttribute("response", createProductResponse);
        return new ModelAndView("redirect:/products");
    }
}

