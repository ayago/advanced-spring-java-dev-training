package com.ourecommerce.ordermanagement.app.db.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "om_order")
public class OrderDocument{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String status;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderItemDocument> items;
    
    public Long getId(){
        return id;
    }
    
    public OrderDocument setId(Long id){
        this.id = id;
        return this;
    }
    
    public String getStatus(){
        return status;
    }
    
    public OrderDocument setStatus(String status){
        this.status = status;
        return this;
    }
    
    public List<OrderItemDocument> getItems(){
        return items;
    }
    
    public OrderDocument setItems(List<OrderItemDocument> items){
        this.items = items;
        return this;
    }
}

