package com.ourecommerce.ordermanagement.app.service;

import com.ourecommerce.ordermanagement.api.OrderDetails;
import com.ourecommerce.ordermanagement.api.OrderPlaced;
import com.ourecommerce.ordermanagement.api.PlaceOrder;
import com.ourecommerce.ordermanagement.api.PlaceOrderResponse;
import com.ourecommerce.ordermanagement.api.TakenOrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    
    private final OrderEventsSender orderEventsSender;
    
    public OrderService(OrderEventsSender orderEventsSender){
        this.orderEventsSender = orderEventsSender;
    }
    
    public TakenOrderResponse takeOrder(OrderDetails orderDetails) {
        // Business logic for adding a new product
        return new TakenOrderResponse("BOOKED");
    }
    
    public PlaceOrderResponse placeOrder(PlaceOrder placeOrder) {
        OrderPlaced orderPlaced = constructEvent(placeOrder);
        orderEventsSender.reserveItems(orderPlaced);
        return new PlaceOrderResponse("BOOKED");
    }
    
    private OrderPlaced constructEvent(PlaceOrder placeOrder){
        return new OrderPlaced(placeOrder.getItemCode(), placeOrder.getCount());
    }
}
