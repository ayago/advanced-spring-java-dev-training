package com.ourecommerce.ordermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/order-events")
public class OrderEventsController {
    
    private final OrderEventsService orderEventsService;
    
    @Autowired
    public OrderEventsController(OrderEventsService orderEventsService) {
        this.orderEventsService = orderEventsService;
    }
    
    @GetMapping
    public Flux<OrderEvent> getOrderEvents() {
        return orderEventsService.getOrderEvents();
    }
}

