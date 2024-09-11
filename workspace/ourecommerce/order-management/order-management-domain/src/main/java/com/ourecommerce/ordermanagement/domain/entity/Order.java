package com.ourecommerce.ordermanagement.domain.entity;

import java.util.List;

public class Order{
    private OrderId id;
    
    private String status;
    
    private List<OrderItem> items;
    
    public OrderId getId(){
        return id;
    }
    
    public Order setId(OrderId id){
        this.id = id;
        return this;
    }
    
    public String getStatus(){
        return status;
    }
    
    public Order setStatus(String status){
        this.status = status;
        return this;
    }
    
    public List<OrderItem> getItems(){
        return items;
    }
    
    public Order setItems(List<OrderItem> items){
        this.items = items;
        return this;
    }
    
    public static class OrderItem{
        private Long id;
        
        private String productId;
        
        private Integer count;
        
        public Long getId(){
            return id;
        }
        
        public OrderItem setId(Long id){
            this.id = id;
            return this;
        }
        
        public String getProductId(){
            return productId;
        }
        
        public OrderItem setProductId(String productId){
            this.productId = productId;
            return this;
        }
        
        public Integer getCount(){
            return count;
        }
        
        public OrderItem setCount(Integer count){
            this.count = count;
            return this;
        }
    }
}
