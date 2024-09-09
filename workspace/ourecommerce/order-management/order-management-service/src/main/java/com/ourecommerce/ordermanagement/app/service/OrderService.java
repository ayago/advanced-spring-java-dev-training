package com.ourecommerce.ordermanagement.app.service;

import com.ourecommerce.ordermanagement.api.OrderDetails;
import com.ourecommerce.ordermanagement.api.OrderPlaced;
import com.ourecommerce.ordermanagement.api.PlaceOrder;
import com.ourecommerce.ordermanagement.api.PlaceOrder.PlaceOrderItem;
import com.ourecommerce.ordermanagement.api.PlaceOrderResponse;
import com.ourecommerce.ordermanagement.api.TakenOrderResponse;
import com.ourecommerce.ordermanagement.app.entity.Order;
import com.ourecommerce.ordermanagement.app.entity.OrderItem;
import com.ourecommerce.ordermanagement.app.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    
    private final OrderEventsSender orderEventsSender;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    
    public OrderService(
        OrderEventsSender orderEventsSender,
        OrderRepository orderRepository,
        ProductService productService
    ){
        this.orderEventsSender = orderEventsSender;
        this.orderRepository = orderRepository;
        this.productService = productService;
    }
    
    public TakenOrderResponse takeOrder(OrderDetails orderDetails) {
        // Business logic for adding a new product
        return new TakenOrderResponse("BOOKED");
    }
    
    public Mono<PlaceOrderResponse> placeOrder(PlaceOrder placeOrder) {
        return Mono.from(resolveOrderFrom(placeOrder))
            .map(orderRepository::save)
            .doOnSuccess(orderRecord -> {
                OrderPlaced orderPlaced = constructEvent(orderRecord);
                orderEventsSender.reserveItems(orderPlaced);
            })
            .map(orderRecord -> new PlaceOrderResponse(orderRecord.getId(), "BOOKED"));
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
                orderItem.setProductId(product.productId);
                return orderItem;
            });
    }
    
    private  OrderPlaced constructEvent(Order orderRecord){
        return new OrderPlaced()
            .setItems(
                orderRecord.getItems().stream()
                    .map(item -> new OrderPlaced.OrderPlacedItem()
                        .setCount(item.getCount())
                        .setProductId(item.getProductId())
                    )
                    .toList()
            );
    }
}
