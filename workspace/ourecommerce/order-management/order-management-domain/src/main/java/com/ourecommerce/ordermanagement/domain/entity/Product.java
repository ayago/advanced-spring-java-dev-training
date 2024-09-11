package com.ourecommerce.ordermanagement.domain.entity;

public class Product{
    
    private final String productId;
    
    public Product(String productId){
        this.productId = productId;
    }
    
    public String getProductId(){
        return productId;
    }
}
