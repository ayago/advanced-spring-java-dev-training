package com.ourecommerce.ordermanagement.app.db;

import com.ourecommerce.ordermanagement.app.db.entity.OrderRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRecordRepositoryTest{
    
    @Autowired
    private OrderRecordRepository orderRecordRepository;
    
    @BeforeEach
    void setUp() {
        // Any setup before each test can be added here if needed
    }
    
    @Test
    void testCreateOrder() {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setStatus("Delivered");
        OrderRecord savedOrderRecord = orderRecordRepository.save(orderRecord);
        
        assertThat(savedOrderRecord).isNotNull();
        assertThat(savedOrderRecord.getId()).isNotNull();
    }
    
    @Test
    void testUpdateOrder() {
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setStatus("Pending");
        OrderRecord savedOrderRecord = orderRecordRepository.save(orderRecord);
        
        savedOrderRecord.setStatus("Completed");
        OrderRecord updatedOrderRecord = orderRecordRepository.save(savedOrderRecord);
        
        assertThat(updatedOrderRecord.getStatus()).isEqualTo("Completed");
    }
    
    @Test
    void testFindById() {
        OrderRecord orderRecord = orderRecordRepository.findById(1L).orElse(null);
        assertThat(orderRecord).isNotNull();
        assertThat(orderRecord.getStatus()).isEqualTo("Pending");
    }
    
    @Test
    void testFindAll() {
        List<OrderRecord> orderRecords = orderRecordRepository.findAll();
        assertThat(orderRecords).isNotEmpty();
        assertThat(orderRecords.size()).isGreaterThan(0);
    }
}
