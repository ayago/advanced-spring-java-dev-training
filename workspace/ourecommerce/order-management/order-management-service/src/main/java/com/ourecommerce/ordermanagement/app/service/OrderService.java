package com.ourecommerce.ordermanagement.app.service;

import com.ourecommerce.ordermanagement.api.OrderDetails;
import com.ourecommerce.ordermanagement.api.TakenOrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    
    public TakenOrderResponse takeOrder(OrderDetails orderDetails) {
        // Business logic for adding a new product
        return new TakenOrderResponse("BOOKED");
    }
}
