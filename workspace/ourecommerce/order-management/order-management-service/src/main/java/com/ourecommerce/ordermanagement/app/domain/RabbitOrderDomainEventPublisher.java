package com.ourecommerce.ordermanagement.app.domain;

import com.ourecommerce.ordermanagement.api.OrderPlaced;
import com.ourecommerce.ordermanagement.domain.adapters.OrderDomainEventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderDomainEventPublisher implements OrderDomainEventPublisher{
    
    private static final String PLACED_ORDER_QUEUE = "new_items";
    private final RabbitTemplate rabbitTemplate;
    
    public RabbitOrderDomainEventPublisher(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Override
    public void publishPlacedOrderEvent(OrderPlaced orderPlaced){
        rabbitTemplate.convertAndSend(PLACED_ORDER_QUEUE, orderPlaced);
    }
}
