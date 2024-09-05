package com.ourecommerce.ordermanagement.app.repository;

import com.ourecommerce.ordermanagement.app.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @BeforeEach
    void setUp() {
        // Any setup before each test can be added here if needed
    }
    
    @Test
    void testCreateOrder() {
        Order order = new Order();
        order.setStatus("Delivered");
        Order savedOrder = orderRepository.save(order);
        
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isGreaterThan(0);
    }
    
    @Test
    void testUpdateOrder() {
        Order order = new Order();
        order.setStatus("Pending");
        Order savedOrder = orderRepository.save(order);
        
        savedOrder.setStatus("Completed");
        Order updatedOrder = orderRepository.save(savedOrder);
        
        assertThat(updatedOrder.getStatus()).isEqualTo("Completed");
    }
    
    @Test
    void testFindById() {
        Order order = orderRepository.findById(1L).orElse(null);
        assertThat(order).isNotNull();
        assertThat(order.getStatus()).isEqualTo("Pending");
    }
    
    @Test
    void testFindAll() {
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).isNotEmpty();
        assertThat(orders.size()).isGreaterThan(0);
    }
}
