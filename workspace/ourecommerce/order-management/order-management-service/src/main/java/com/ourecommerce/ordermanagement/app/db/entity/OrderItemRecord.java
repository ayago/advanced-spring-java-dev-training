package com.ourecommerce.ordermanagement.app.db.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "om_order_item")
public class OrderItemRecord{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_id", nullable = false)
    private String productId;
    
    @Column(nullable = false)
    private Integer count;
    
    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private OrderRecord owningOrder;
    
    public OrderRecord getOwningOrder(){
        return owningOrder;
    }
    
    public OrderItemRecord setOwningOrder(OrderRecord owningOrder){
        this.owningOrder = owningOrder;
        return this;
    }
    
    public Long getId(){
        return id;
    }
    
    public OrderItemRecord setId(Long id){
        this.id = id;
        return this;
    }
    
    public String getProductId(){
        return productId;
    }
    
    public OrderItemRecord setProductId(String productId){
        this.productId = productId;
        return this;
    }
    
    public Integer getCount(){
        return count;
    }
    
    public OrderItemRecord setCount(Integer count){
        this.count = count;
        return this;
    }
}
