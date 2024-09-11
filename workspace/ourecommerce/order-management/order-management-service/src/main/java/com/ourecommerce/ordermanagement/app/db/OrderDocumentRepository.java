package com.ourecommerce.ordermanagement.app.db;

import com.ourecommerce.ordermanagement.app.db.entity.OrderDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDocumentRepository extends JpaRepository<OrderDocument, Long>{

}
