package com.ourecommerce.webapps.mvc.dto;

public class OrderResponse {
    private String status;
    private int orderId;
    
    public String getStatus(){
        return status;
    }
    
    public OrderResponse setStatus(String status){
        this.status = status;
        return this;
    }
    
    public int getOrderId(){
        return orderId;
    }
    
    public OrderResponse setOrderId(int orderId){
        this.orderId = orderId;
        return this;
    }
}

