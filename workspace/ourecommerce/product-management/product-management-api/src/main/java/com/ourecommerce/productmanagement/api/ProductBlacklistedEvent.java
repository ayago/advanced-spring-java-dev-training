package com.ourecommerce.productmanagement.api;

public class ProductBlacklistedEvent{
    private String productId;
    
    public ProductBlacklistedEvent(String productId){
        this.productId = productId;
    }
    
    public ProductBlacklistedEvent(){
    }
    
    public void setProductId(String productId){
        this.productId = productId;
    }
    
    public String getProductId(){
        return productId;
    }
    
    @Override
    public String toString(){
        return "ProductBlacklistedEvent{" +
            "productId='" + productId + '\'' +
            '}';
    }
}
