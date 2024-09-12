package com.ourecommerce.ordermanagement.app.db.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "om_order")
public class OrderRecord{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String status;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "owningOrder")
    private List<OrderItemRecord> items;
    
    public Long getId(){
        return id;
    }
    
    public OrderRecord setId(Long id){
        this.id = id;
        return this;
    }
    
    public String getStatus(){
        return status;
    }
    
    public OrderRecord setStatus(String status){
        this.status = status;
        return this;
    }
    
    public List<OrderItemRecord> getItems(){
        return items;
    }
    
    public OrderRecord setItems(List<OrderItemRecord> items){
        this.items = items;
        return this;
    }
}

