package com.ourecommerce.inventorymanagement.app.eventhandlers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReserveItemsHandler{
    
    @RabbitListener(queues = "new_items")
    public void receiveMessage(String message) {
        try{
            Thread.sleep(3000);
            System.out.println("Received: " + message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    @RabbitListener(queues = "inventory_product_catalog_queue")
    public void processBlackList(String message) {
        System.out.println("Processing blacklisted product: " + message);
    }
}
