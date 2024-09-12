package com.ourecommerce.productmanagement.api;

public class ProductDetailsRequest{
    private String name;
    private String description;
    
    public ProductDetailsRequest setName(String name){
        this.name = name;
        return this;
    }
    
    public ProductDetailsRequest setDescription(String description){
        this.description = description;
        return this;
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
