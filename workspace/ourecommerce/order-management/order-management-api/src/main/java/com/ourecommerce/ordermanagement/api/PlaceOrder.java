package com.ourecommerce.ordermanagement.api;

public class PlaceOrder{
    private final String itemCode;
    private final Integer count;
    
    public PlaceOrder(String itemCode, Integer count){
        this.itemCode = itemCode;
        this.count = count;
    }
    
    public String getItemCode(){
        return itemCode;
    }
    
    public Integer getCount(){
        return count;
    }
}
