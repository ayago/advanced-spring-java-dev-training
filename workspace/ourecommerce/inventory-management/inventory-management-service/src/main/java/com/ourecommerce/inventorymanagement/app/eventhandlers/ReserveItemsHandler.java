package com.ourecommerce.inventorymanagement.app.eventhandlers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReserveItemsHandler{
    @RabbitListener(queues = "reserve_items")
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }
}
