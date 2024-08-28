package com.ourecommerce.productmanagement.api;

public class AddNewProductResponse{
    private final String productId;
    
    public AddNewProductResponse(String productId){
        this.productId = productId;
    }
    
    public String getProductId(){
        return productId;
    }
    
    @Override
    public String toString(){
        return "AddNewProductResponse{" +
            "productId='" + productId + '\'' +
            '}';
    }
}
