package com.ourecommerce.ordermanagement.app.config;

import com.ourecommerce.ordermanagement.domain.DefaultOrderService;
import com.ourecommerce.ordermanagement.domain.adapters.OrderDomainEventPublisher;
import com.ourecommerce.ordermanagement.domain.adapters.OrderDomainRepository;
import com.ourecommerce.ordermanagement.domain.OrderService;
import com.ourecommerce.ordermanagement.domain.adapters.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration{
    
    @Bean
    public OrderService orderService(
        ProductService productService,
        OrderDomainRepository orderDomainRepository,
        OrderDomainEventPublisher orderDomainEventPublisher
    ){
        return new DefaultOrderService(orderDomainRepository, productService, orderDomainEventPublisher);
    }
}
