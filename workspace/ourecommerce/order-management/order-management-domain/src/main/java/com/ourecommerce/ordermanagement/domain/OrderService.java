package com.ourecommerce.ordermanagement.domain;

import com.ourecommerce.ordermanagement.api.PlaceOrder;
import com.ourecommerce.ordermanagement.api.PlaceOrderResponse;
import reactor.core.publisher.Mono;

public interface OrderService{
    Mono<PlaceOrderResponse> placeOrder(PlaceOrder placeOrder);
}
