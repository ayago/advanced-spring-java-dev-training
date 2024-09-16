package com.ourecommerce.ordermanagement.domain;

import com.ourecommerce.ordermanagement.api.OrderPlaced;
import com.ourecommerce.ordermanagement.api.PlaceOrder;
import com.ourecommerce.ordermanagement.api.PlaceOrder.PlaceOrderItem;
import com.ourecommerce.ordermanagement.api.PlaceOrderResponse;
import com.ourecommerce.ordermanagement.domain.adapters.OrderDomainEventPublisher;
import com.ourecommerce.ordermanagement.domain.adapters.OrderDomainRepository;
import com.ourecommerce.ordermanagement.domain.adapters.ProductService;
import com.ourecommerce.ordermanagement.domain.entity.Order;
import com.ourecommerce.ordermanagement.domain.entity.Order.OrderItem;
import com.ourecommerce.ordermanagement.domain.entity.OrderId;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService{
    
    private final OrderDomainRepository orderDomainRepository;
    private final ProductService productService;
    private final OrderDomainEventPublisher orderDomainEventPublisher;
    
    public DefaultOrderService(
        OrderDomainRepository orderDomainRepository,
        ProductService productService,
        OrderDomainEventPublisher orderDomainEventPublisher
    ){
        this.orderDomainRepository = orderDomainRepository;
        this.productService = productService;
        this.orderDomainEventPublisher = orderDomainEventPublisher;
    }
    
    @Override
    public Mono<PlaceOrderResponse> placeOrder(PlaceOrder placeOrder){
        return resolveOrderFrom(placeOrder)
            .flatMap(order -> orderDomainRepository.save(order)
                .doOnSuccess(orderId -> {
                    OrderPlaced orderPlaced = constructEvent(order, orderId);
                    orderDomainEventPublisher.publishPlacedOrderEvent(orderPlaced);
                })
                .map(orderId -> new PlaceOrderResponse()
                    .setOrderId(orderId.toString())
                    .setStatus(order.getStatus())
                )
            );
    }
    
    private OrderPlaced constructEvent(Order order, OrderId orderId){
        return new OrderPlaced()
            .setOrderId(orderId.toString())
            .setItems(
                order.getItems().stream()
                    .map(item -> new OrderPlaced.OrderPlacedItem()
                        .setCount(item.getCount())
                        .setProductId(item.getProductId())
                    )
                    .toList()
            );
    }
    
    private Mono<Order> resolveOrderFrom(PlaceOrder placeOrder){
        return resolveItemsFrom(placeOrder)
            .map(orderedItems -> {
                Order order = new Order();
                order.setStatus("BOOKED");
                order.setItems(orderedItems);
                return order;
            });
    }
    
    private Mono<List<OrderItem>> resolveItemsFrom(PlaceOrder placeOrder){
        List<Mono<OrderItem>> collected = placeOrder.getItems().stream()
            .map(this::resolveOrderItem)
            .collect(Collectors.toList());
        
        return Mono.zip(collected, orderItems -> Arrays.stream(orderItems).map(OrderItem.class::cast).toList());
    }
    
    private Mono<OrderItem> resolveOrderItem(PlaceOrderItem item){
        
        return productService.getProductWithCode(item.getProductId())
            .map(product -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setCount(item.getCount());
                orderItem.setProductId(product.getProductId());
                return orderItem;
            });
    }
}
