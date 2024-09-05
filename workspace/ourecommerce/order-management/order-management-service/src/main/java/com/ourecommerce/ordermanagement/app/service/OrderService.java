package com.ourecommerce.ordermanagement.app.service;

import com.ourecommerce.ordermanagement.api.OrderDetails;
import com.ourecommerce.ordermanagement.api.OrderPlaced;
import com.ourecommerce.ordermanagement.api.PlaceOrder;
import com.ourecommerce.ordermanagement.api.PlaceOrderResponse;
import com.ourecommerce.ordermanagement.api.TakenOrderResponse;
import com.ourecommerce.ordermanagement.app.entity.Order;
import com.ourecommerce.ordermanagement.app.entity.OrderItem;
import com.ourecommerce.ordermanagement.app.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    
    private final OrderEventsSender orderEventsSender;
    private final OrderRepository orderRepository;
    
    public OrderService(OrderEventsSender orderEventsSender, OrderRepository orderRepository){
        this.orderEventsSender = orderEventsSender;
        this.orderRepository = orderRepository;
    }
    
    public TakenOrderResponse takeOrder(OrderDetails orderDetails) {
        // Business logic for adding a new product
        return new TakenOrderResponse("BOOKED");
    }
    
    public PlaceOrderResponse placeOrder(PlaceOrder placeOrder) {
        Order orderRecord = resolveOrderFrom(placeOrder);
        orderRepository.save(orderRecord);
        
        OrderPlaced orderPlaced = constructEvent(orderRecord);
        orderEventsSender.reserveItems(orderPlaced);
        return new PlaceOrderResponse(orderRecord.getId(), "BOOKED");
    }
    
    private Order resolveOrderFrom(PlaceOrder placeOrder){
        Order order = new Order();
        order.setStatus("BOOKED");
        
        List<OrderItem> orderedItems = resolveItemsFrom(placeOrder);
        order.setItems(orderedItems);
        return order;
    }
    
    private static List<OrderItem> resolveItemsFrom(PlaceOrder placeOrder){
        return placeOrder.getItems().stream()
            .map(item -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setCount(item.getCount());
                orderItem.setProductId(item.getProductId());
                return orderItem;
            })
            .collect(Collectors.toList());
    }
    
    private static OrderPlaced constructEvent(Order orderRecord){
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
