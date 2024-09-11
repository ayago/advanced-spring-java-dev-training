package com.ourecommerce.ordermanagement.domain.adapters;

import com.ourecommerce.ordermanagement.api.OrderPlaced;

public interface OrderDomainEventPublisher{
    void publishPlacedOrderEvent(OrderPlaced orderPlaced);
}
