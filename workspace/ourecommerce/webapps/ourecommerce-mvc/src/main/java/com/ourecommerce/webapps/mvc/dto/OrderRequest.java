package com.ourecommerce.webapps.mvc.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    private List<OrderItem> items = new ArrayList<>();
    
    public List<OrderItem> getItems(){
        return items;
    }
    
    public OrderRequest setItems(List<OrderItem> items){
        this.items = items;
        return this;
    }
    
    public static class OrderItem{
        private String productId;
        private int count;
        
        public String getProductId(){
            return productId;
        }
        
        public OrderItem setProductId(String productId){
            this.productId = productId;
            return this;
        }
        
        public int getCount(){
            return count;
        }
        
        public OrderItem setCount(int count){
            this.count = count;
            return this;
        }
    }
}

