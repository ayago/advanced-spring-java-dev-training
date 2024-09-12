package com.ourecommerce.webapps.mvc.dto;

public class ProductResponse {
    private String productId;
    
    public String getProductId(){
        return productId;
    }
    
    public ProductResponse setProductId(String productId){
        this.productId = productId;
        return this;
    }
}

