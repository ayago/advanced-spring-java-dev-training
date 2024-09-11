package com.ourecommerce.ordermanagement.app.controller;

import com.ourecommerce.ordermanagement.api.OrderDetails;
import com.ourecommerce.ordermanagement.api.PlaceOrder;
import com.ourecommerce.ordermanagement.api.PlaceOrderResponse;
import com.ourecommerce.ordermanagement.domain.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    private final OrderService orderService;
    
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping
    public Mono<ResponseEntity<PlaceOrderResponse>> placeOrder(@RequestBody PlaceOrder orderDetails) {
        Mono<ResponseEntity<PlaceOrderResponse>> response =  Mono.delay(Duration.ofSeconds(1))
            .flatMap(it -> orderService.placeOrder(orderDetails))
            .map(ResponseEntity::ok)
            .doOnSuccess(success -> System.out.println(Instant.now() + " Done with requested order "+orderDetails));
        
        System.out.println(Instant.now() + " Requested order "+orderDetails);
        
        return response;
    }
    
    @GetMapping
    public ResponseEntity<OrderDetails> retrieveOrder(){
        return ResponseEntity.ok(new OrderDetails("Pandesal", "Pinoy Bread"));
    }
}

