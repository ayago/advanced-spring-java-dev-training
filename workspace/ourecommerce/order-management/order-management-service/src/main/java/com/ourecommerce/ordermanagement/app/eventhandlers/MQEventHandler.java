package com.ourecommerce.ordermanagement.app.eventhandlers;

import com.ourecommerce.productmanagement.api.ProductBlacklistedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MQEventHandler{
    @RabbitListener(queues = "order_product_catalog_queue")
    public void processBlackList(ProductBlacklistedEvent message) {
        System.out.println("Processing blacklisted product: " + message);
    }
}
