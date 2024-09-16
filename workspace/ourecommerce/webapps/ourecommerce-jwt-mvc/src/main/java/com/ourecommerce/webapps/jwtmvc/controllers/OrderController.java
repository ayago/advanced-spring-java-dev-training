package com.ourecommerce.webapps.jwtmvc.controllers;

import com.ourecommerce.ordermanagement.api.PlaceOrder;
import com.ourecommerce.ordermanagement.api.PlaceOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private WebClient webClient;
    
    @PostMapping
    public PlaceOrderResponse createOrder(@RequestBody PlaceOrder orderRequest) {
        return webClient.post()
            .uri("/orders")
            .bodyValue(orderRequest)
            .retrieve()
            .bodyToMono(PlaceOrderResponse.class)
            .block();
    }
}

