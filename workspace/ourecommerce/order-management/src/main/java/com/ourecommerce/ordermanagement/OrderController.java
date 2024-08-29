package com.ourecommerce.ordermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<TakenOrderResponse>> takeOrder(@RequestBody OrderDetails orderDetails) {
        return Mono.fromSupplier(() -> {
                simulateNetworkDelay();
                return orderService.takeOrder(orderDetails);
            })
            .map(ResponseEntity::ok);
    }

    private void simulateNetworkDelay(){
        
        try {
            Thread.sleep(2000);  // Simulate a 2-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

