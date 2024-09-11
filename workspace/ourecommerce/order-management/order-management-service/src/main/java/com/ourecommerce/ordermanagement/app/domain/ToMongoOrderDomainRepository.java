package com.ourecommerce.ordermanagement.app.domain;

import com.ourecommerce.ordermanagement.app.db.OrderDocumentRepository;
import com.ourecommerce.ordermanagement.app.db.entity.OrderDocument;
import com.ourecommerce.ordermanagement.app.db.entity.OrderItemDocument;
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
    
    private final OrderDocumentRepository orderDocumentRepository;
    
    public ToMongoOrderDomainRepository(OrderDocumentRepository orderDocumentRepository){
        this.orderDocumentRepository = orderDocumentRepository;
    }
    
    @Override
    @Transactional
    public Mono<OrderId> save(Order order){
        return Mono.fromSupplier(() -> convertToDocument(order))
            .map(orderDocumentRepository::save)
            .map(od -> new OrderId(od.getId()));
    }
    
    private OrderDocument convertToDocument(Order order){
        
        OrderDocument documentEquivalent = Optional.ofNullable(order.getId())
            .map(id -> new OrderDocument()
                .setId(order.getId().getRawValue()))
            .orElseGet(OrderDocument::new);
        
        return documentEquivalent
            .setStatus(order.getStatus())
            .setItems(convertToDocument(order.getItems()));
    }
    
    private List<OrderItemDocument> convertToDocument(List<OrderItem> items){
        return items.stream()
            .map(this::convertToDocument)
            .toList();
    }
    
    private OrderItemDocument convertToDocument(OrderItem orderItem){
        OrderItemDocument documentEquivalent = Optional.ofNullable(orderItem.getId())
            .map(orderItemId -> new OrderItemDocument().setId(orderItemId))
            .orElseGet(OrderItemDocument::new);
        
        return documentEquivalent
            .setCount(orderItem.getCount())
            .setProductId(orderItem.getProductId());
    }
}
