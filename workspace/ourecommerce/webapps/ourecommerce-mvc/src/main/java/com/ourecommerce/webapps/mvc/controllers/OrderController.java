package com.ourecommerce.webapps.mvc.controllers;

import com.ourecommerce.webapps.mvc.dto.OrderRequest;
import com.ourecommerce.webapps.mvc.dto.OrderResponse;
import com.ourecommerce.webapps.mvc.dto.Product;
import com.ourecommerce.webapps.mvc.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OrderController {
    
    @Autowired
    private WebClient webClient;
    
    @PostMapping("/orders")
    public ModelAndView createOrder(@ModelAttribute OrderRequest orderRequest, Model model) {
        OrderResponse orderResponse = webClient.post()
            .uri("/orders")
            .bodyValue(orderRequest)
            .retrieve()
            .bodyToMono(OrderResponse.class)
            .block();
        
        model.addAttribute("orderResponse", orderResponse);
        return new ModelAndView("redirect:/orders");
    }
    
    @GetMapping("/orders")
    public String getOrderForm(Model model) {
        List<Product> products = webClient
            .get()
            .uri("/products")
            .retrieve()
            .bodyToFlux(Product.class)
            .collectList()
            .block();
        
        model.addAttribute("orderRequest", new OrderRequest());
        model.addAttribute("products", products); // Block for simplicity; avoid in production
        return "orders";
    }
}

