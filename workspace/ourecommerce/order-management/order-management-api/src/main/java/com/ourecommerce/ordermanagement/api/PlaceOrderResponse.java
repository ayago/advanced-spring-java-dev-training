package com.ourecommerce.ordermanagement.api;

public class PlaceOrderResponse{
    
    private final String status;
    
    public PlaceOrderResponse(String status){
        
        this.status = status;
    }
    
    public String getStatus(){
        return status;
    }
}
