package com.ourecommerce.productmanagement.app.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pm_product")
public class Product{
    
    @Id
    private String id;
    private String name;
    private String description;
    private String status;
    
    public String getId(){
        return id;
    }
    
    public Product setId(String id){
        this.id = id;
        return this;
    }
    
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
    
    public String getStatus(){
        return status;
    }
    
    public Product setStatus(String status){
        this.status = status;
        return this;
    }
}
