package com.ourecommerce.productmanagement.api;

public class ProductDetailsResponse{
    private String productCode;
    private String name;
    private String description;
    private String status;
    
    public String getProductCode(){
        return productCode;
    }
    
    public ProductDetailsResponse setProductCode(String productCode){
        this.productCode = productCode;
        return this;
    }
    
    public String getName(){
        return name;
    }
    
    public ProductDetailsResponse setName(String name){
        this.name = name;
        return this;
    }
    
    public String getDescription(){
        return description;
    }
    
    public ProductDetailsResponse setDescription(String description){
        this.description = description;
        return this;
    }
    
    public String getStatus(){
        return status;
    }
    
    public ProductDetailsResponse setStatus(String status){
        this.status = status;
        return this;
    }
}
