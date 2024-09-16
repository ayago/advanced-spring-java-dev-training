package com.ourecommerce.productmanagement.api;

public class AddNewProductResponse{
    private String productId;
    
    public AddNewProductResponse setProductId(String productId){
        this.productId = productId;
        return this;
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
