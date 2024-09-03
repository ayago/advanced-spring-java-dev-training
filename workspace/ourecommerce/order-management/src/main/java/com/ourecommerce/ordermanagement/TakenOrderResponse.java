package com.ourecommerce.ordermanagement;

public class TakenOrderResponse{
    
    private final String status;
    
    public TakenOrderResponse(String status){
        this.status = status;
    }
    
    public String getStatus(){
        return status;
    }
}
