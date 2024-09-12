package com.ourecommerce.webapps.mvc.dto;

public class Product {
    private String name;
    private String description;
    private String productCode;
    private String status;
    
    public String getName(){
        return name;
    }
    
    public Product setName(String name){
        this.name = name;
        return this;
    }
    
    public String getDescription(){
        return description;
    }
    
    public Product setDescription(String description){
        this.description = description;
        return this;
    }
    
    public String getProductCode(){
        return productCode;
    }
    
    public Product setProductCode(String productCode){
        this.productCode = productCode;
        return this;
    }
    
    public String getStatus(){
        return status;
    }
    
    public Product setStatus(String status){
        this.status = status;
        return this;
    }
    
    @Override
    public String toString(){
        return "{" +
            "name: " + "\"" + name + "\"" + "," +
            "description: " + "\"" + description + "\"" + "," +
            "productCode: " + "\"" + productCode + "\"" + "," +
            "status: " + "\"" + status + "\"" +
            "}";
    }
}

