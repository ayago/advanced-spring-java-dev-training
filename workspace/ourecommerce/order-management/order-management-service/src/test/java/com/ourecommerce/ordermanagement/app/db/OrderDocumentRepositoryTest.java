package com.ourecommerce.ordermanagement.app.db;

import com.ourecommerce.ordermanagement.app.db.entity.OrderDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderDocumentRepositoryTest{
    
    @Autowired
    private OrderDocumentRepository orderDocumentRepository;
    
    @BeforeEach
    void setUp() {
        // Any setup before each test can be added here if needed
    }
    
    @Test
    void testCreateOrder() {
        OrderDocument orderDocument = new OrderDocument();
        orderDocument.setStatus("Delivered");
        OrderDocument savedOrderDocument = orderDocumentRepository.save(orderDocument);
        
        assertThat(savedOrderDocument).isNotNull();
        assertThat(savedOrderDocument.getId()).isNotNull();
    }
    
    @Test
    void testUpdateOrder() {
        OrderDocument orderDocument = new OrderDocument();
        orderDocument.setStatus("Pending");
        OrderDocument savedOrderDocument = orderDocumentRepository.save(orderDocument);
        
        savedOrderDocument.setStatus("Completed");
        OrderDocument updatedOrderDocument = orderDocumentRepository.save(savedOrderDocument);
        
        assertThat(updatedOrderDocument.getStatus()).isEqualTo("Completed");
    }
    
    @Test
    void testFindById() {
        OrderDocument orderDocument = orderDocumentRepository.findById(1L).orElse(null);
        assertThat(orderDocument).isNotNull();
        assertThat(orderDocument.getStatus()).isEqualTo("Pending");
    }
    
    @Test
    void testFindAll() {
        List<OrderDocument> orderDocuments = orderDocumentRepository.findAll();
        assertThat(orderDocuments).isNotEmpty();
        assertThat(orderDocuments.size()).isGreaterThan(0);
    }
}
