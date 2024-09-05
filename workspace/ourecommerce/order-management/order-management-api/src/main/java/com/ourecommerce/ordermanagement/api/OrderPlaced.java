package com.ourecommerce.ordermanagement.api;

import java.util.List;

public class OrderPlaced{
    
    private List<OrderPlacedItem> items;
    
    public OrderPlaced setItems(List<OrderPlacedItem> items){
        this.items = items;
        return this;
    }
    
    public List<OrderPlacedItem> getItems(){
        return items;
    }
    
    public static class OrderPlacedItem{
        private String productId;
        private Integer count;
        
        public String getProductId(){
            return productId;
        }
        
        public OrderPlacedItem setProductId(String productId){
            this.productId = productId;
            return this;
        }
        
        public Integer getCount(){
            return count;
        }
        
        public OrderPlacedItem setCount(Integer count){
            this.count = count;
            return this;
        }
    }
}
