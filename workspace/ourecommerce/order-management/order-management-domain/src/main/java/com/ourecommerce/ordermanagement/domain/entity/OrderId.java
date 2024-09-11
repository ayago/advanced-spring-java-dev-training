package com.ourecommerce.ordermanagement.domain.entity;

public class OrderId{
    private final Long id;
    public OrderId(Long id){
        this.id = id;
    }
    
    public Long getRawValue(){
        return id;
    }
    
    @Override
    public String toString(){
        return id.toString();
    }
}
