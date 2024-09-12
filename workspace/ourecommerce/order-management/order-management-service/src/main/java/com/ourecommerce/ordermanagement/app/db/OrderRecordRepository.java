package com.ourecommerce.ordermanagement.app.db;

import com.ourecommerce.ordermanagement.app.db.entity.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRecordRepository extends JpaRepository<OrderRecord, Long>{

}
