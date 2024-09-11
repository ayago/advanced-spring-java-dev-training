package com.ourecommerce.ordermanagement.app.db.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "om_order_item")
public class OrderItemDocument{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_id", nullable = false)
    private String productId;
    
    @Column(nullable = false)
    private Integer count;
    
    public Long getId(){
        return id;
    }
    
    public OrderItemDocument setId(Long id){
        this.id = id;
        return this;
    }
    
    public String getProductId(){
        return productId;
    }
    
    public OrderItemDocument setProductId(String productId){
        this.productId = productId;
        return this;
    }
    
    public Integer getCount(){
        return count;
    }
    
    public OrderItemDocument setCount(Integer count){
        this.count = count;
        return this;
    }
}
