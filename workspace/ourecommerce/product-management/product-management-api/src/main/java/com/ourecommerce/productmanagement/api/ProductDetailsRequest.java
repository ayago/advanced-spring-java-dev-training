package com.ourecommerce.productmanagement.api;

public class ProductDetailsRequest{
    private final String name;
    private final String description;
    
    public ProductDetailsRequest(String name, String description){
        this.name = name;
        this.description = description;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
    
    @Override
    public String toString(){
        return "ProductDetailsRequest{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
