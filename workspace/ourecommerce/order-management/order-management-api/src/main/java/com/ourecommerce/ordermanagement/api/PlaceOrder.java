package com.ourecommerce.ordermanagement.api;

import java.util.List;

public class PlaceOrder{
    private List<PlaceOrderItem> items;
    
    public PlaceOrder setItems(List<PlaceOrderItem> items){
        this.items = items;
        return this;
    }
    
    public List<PlaceOrderItem> getItems(){
        return items;
    }
    
    public static class PlaceOrderItem{
        private String productId;
        private Integer count;
        
        public PlaceOrderItem setProductId(String productId){
            this.productId = productId;
            return this;
        }
        
        public PlaceOrderItem setCount(Integer count){
            this.count = count;
            return this;
        }
        
        public String getProductId(){
            return productId;
        }
        
        public Integer getCount(){
            return count;
        }
    }
}
