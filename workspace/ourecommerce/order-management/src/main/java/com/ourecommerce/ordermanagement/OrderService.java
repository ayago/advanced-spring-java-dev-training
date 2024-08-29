package com.ourecommerce.ordermanagement;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    
    public TakenOrderResponse takeOrder(OrderDetails orderDetails) {
        // Business logic for adding a new product
        return new TakenOrderResponse("BOOKED");
    }
}
