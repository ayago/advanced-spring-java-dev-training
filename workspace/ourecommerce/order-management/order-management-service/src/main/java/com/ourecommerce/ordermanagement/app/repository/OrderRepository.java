package com.ourecommerce.ordermanagement.app.repository;

import com.ourecommerce.ordermanagement.app.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
