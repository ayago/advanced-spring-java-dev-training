package com.ourecommerce.ordermanagement.app.controller;

import com.ourecommerce.ordermanagement.api.OrderDetails;
import com.ourecommerce.ordermanagement.api.PlaceOrder;
import com.ourecommerce.ordermanagement.api.PlaceOrderResponse;
import com.ourecommerce.ordermanagement.api.TakenOrderResponse;
import com.ourecommerce.ordermanagement.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    private final OrderService orderService;
    
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping
    public Mono<ResponseEntity<PlaceOrderResponse>> takeOrder(@RequestBody PlaceOrder orderDetails) {
        return orderService.placeOrder(orderDetails)
            .map(ResponseEntity::ok);
    }
    
    @GetMapping
    public ResponseEntity<OrderDetails> retrieveOrder(){
        return ResponseEntity.ok(new OrderDetails("Pandesal", "Pinoy Bread"));
    }

    private void simulateNetworkDelay(){
        
        try {
            Thread.sleep(100);  // Simulate a 2-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

