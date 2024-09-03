package com.ourecommerce.ordermanagement.api;

public class OrderEvent{
    private final String orderId;
    private final String status;
    
    public OrderEvent(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }
    
    public String getOrderId(){
        return orderId;
    }
    
    public String getStatus(){
        return status;
    }
}
