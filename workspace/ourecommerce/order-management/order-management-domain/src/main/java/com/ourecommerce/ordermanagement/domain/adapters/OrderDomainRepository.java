package com.ourecommerce.ordermanagement.domain.adapters;

import com.ourecommerce.ordermanagement.domain.entity.Order;
import com.ourecommerce.ordermanagement.domain.entity.OrderId;
import reactor.core.publisher.Mono;

public interface OrderDomainRepository{
    Mono<OrderId> save(Order order);
}
