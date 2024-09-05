package com.ourecommerce.ordermanagement.api;

public class PlaceOrderResponse{
    
    private final String status;
    private final Long orderId;
    
    public PlaceOrderResponse(Long id, String status){
        this.orderId = id;
        this.status = status;
    }
    
    public String getStatus(){
        return status;
    }
    
    public Long getOrderId(){
        return orderId;
    }
}
