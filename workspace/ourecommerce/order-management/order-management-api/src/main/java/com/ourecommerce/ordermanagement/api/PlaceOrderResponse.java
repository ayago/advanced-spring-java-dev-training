package com.ourecommerce.ordermanagement.api;

public class PlaceOrderResponse{
    
    private String status;
    private String orderId;
    
    public PlaceOrderResponse setStatus(String status){
        this.status = status;
        return this;
    }
    
    public PlaceOrderResponse setOrderId(String orderId){
        this.orderId = orderId;
        return this;
    }
    
    public String getStatus(){
        return status;
    }
    
    public String getOrderId(){
        return orderId;
    }
}
