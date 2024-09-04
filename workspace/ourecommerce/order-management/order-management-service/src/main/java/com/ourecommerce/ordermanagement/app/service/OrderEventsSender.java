package com.ourecommerce.ordermanagement.app.service;

import com.ourecommerce.ordermanagement.api.OrderPlaced;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventsSender{
    
    private final RabbitTemplate rabbitTemplate;
    
    public OrderEventsSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void reserveItems(OrderPlaced message) {
        rabbitTemplate.convertAndSend("reserve_items", message);
    }
}
