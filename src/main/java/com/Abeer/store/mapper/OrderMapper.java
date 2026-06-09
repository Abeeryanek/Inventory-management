package com.abeer.store.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.abeer.store.dto.response.OrderResponse;
import com.abeer.store.entity.Order;
import com.abeer.store.entity.OrderItem;

@Component
public class OrderMapper {
    public OrderResponse.Full toResponse(Order order){
        return new OrderResponse.Full(
            order.getOrderId(),
            order.getOrderName(),
            order.getOrderStatus().name(),
            order.getTotalPrice(),
            order.getUser().getUsername(),
            order.getItems().stream().map(this::toItemResponse).collect(Collectors.toList())

        );
    }

    public OrderResponse.OrderItemResponse toItemResponse(OrderItem orderItem){
        return new OrderResponse.OrderItemResponse(
            orderItem.getOrderItemId(),
            orderItem.getProduct().getProductName(),
            orderItem.getPriceAtPurchase(),
            orderItem.getQuantity()
        );
    }
    
}
