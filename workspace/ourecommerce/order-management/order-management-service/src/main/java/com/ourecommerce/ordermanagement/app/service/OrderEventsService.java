package com.ourecommerce.ordermanagement.app.service;

import com.ourecommerce.ordermanagement.api.OrderEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Service
public class OrderEventsService {
    
    public Flux<OrderEvent> getOrderEvents() {
        // Generate 20 OrderEvents with a delay of 1 second between each
        return Flux.fromStream(Stream.generate(this::generateOrderEvent).limit(20))
            .delayElements(Duration.ofSeconds(1)); // Simulate streaming with delay
    }
    
    private OrderEvent generateOrderEvent() {
        // Generate a dummy OrderEvent (add more realistic fields and logic as needed)
        return new OrderEvent("Order_" + System.currentTimeMillis(), "CREATED");
    }
}

