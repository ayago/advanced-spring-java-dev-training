package com.ourecommerce.ordermanagement.api;

public class PlaceOrderResponse{
    
    private final String status;
    private final String orderId;
    
    public PlaceOrderResponse(String id, String status){
        this.orderId = id;
        this.status = status;
    }
    
    public String getStatus(){
        return status;
    }
    
    public String getOrderId(){
        return orderId;
    }
}
