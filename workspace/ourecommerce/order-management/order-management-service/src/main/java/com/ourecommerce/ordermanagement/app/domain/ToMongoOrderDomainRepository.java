package com.ourecommerce.ordermanagement.app.domain;

import com.ourecommerce.ordermanagement.app.db.OrderRecordRepository;
import com.ourecommerce.ordermanagement.app.db.entity.OrderRecord;
import com.ourecommerce.ordermanagement.app.db.entity.OrderItemRecord;
import com.ourecommerce.ordermanagement.domain.adapters.OrderDomainRepository;
import com.ourecommerce.ordermanagement.domain.entity.Order;
import com.ourecommerce.ordermanagement.domain.entity.Order.OrderItem;
import com.ourecommerce.ordermanagement.domain.entity.OrderId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
public class ToMongoOrderDomainRepository implements OrderDomainRepository{
    
    private final OrderRecordRepository orderRecordRepository;
    
    public ToMongoOrderDomainRepository(OrderRecordRepository orderRecordRepository){
        this.orderRecordRepository = orderRecordRepository;
    }
    
    @Override
    @Transactional
    public Mono<OrderId> save(Order order){
        return Mono.fromSupplier(() -> deriveRecord(order))
            .map(orderRecordRepository::save)
            .map(od -> new OrderId(od.getId()));
    }
    
    private OrderRecord deriveRecord(Order order){
        
        OrderRecord recordEquivalent = Optional.ofNullable(order.getId())
            .map(id -> new OrderRecord()
                .setId(order.getId().getRawValue()))
            .orElseGet(OrderRecord::new);
        
        return recordEquivalent
            .setStatus(order.getStatus())
            .setItems(deriveRecord(recordEquivalent, order.getItems()));
    }
    
    private List<OrderItemRecord> deriveRecord(OrderRecord orderRecord, List<OrderItem> items){
        return items.stream()
            .map(item -> deriveRecord(orderRecord, item))
            .toList();
    }
    
    private OrderItemRecord deriveRecord(OrderRecord orderRecord, OrderItem orderItem){
        OrderItemRecord recordEquivalent = Optional.ofNullable(orderItem.getId())
            .map(orderItemId -> new OrderItemRecord().setId(orderItemId))
            .orElseGet(OrderItemRecord::new);
        
        return recordEquivalent
            .setOwningOrder(orderRecord)
            .setCount(orderItem.getCount())
            .setProductId(orderItem.getProductId());
    }
}
