package com.ourecommerce.productmanagement.app.service;

import com.ourecommerce.productmanagement.api.ProductBlacklistedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductService{
    
    private final RabbitTemplate rabbitTemplate;
    
    public ProductService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void blacklistProduct(String productCode){
        rabbitTemplate.convertAndSend("product_catalog_exchange", "", new ProductBlacklistedEvent(productCode));
    }
}
