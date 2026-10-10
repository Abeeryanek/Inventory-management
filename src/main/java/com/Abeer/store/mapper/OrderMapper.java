package com.abeer.store.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.abeer.store.dto.order.OrderResponse;
import com.abeer.store.dto.orderItem.OrderItemResponse;
import com.abeer.store.entity.Order;
import com.abeer.store.entity.OrderItem;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order){
        return new OrderResponse(
            order.getId(),
            order.getStatus().name(),
            order.getTotalPrice(),
            order.getUser().getUsername(),
            order.getItems().stream().map(this::toItemResponse).collect(Collectors.toList())

        );
    }

    public OrderItemResponse toItemResponse(OrderItem orderItem){
        return new OrderItemResponse(
            orderItem.getId(),
            orderItem.getProduct().getName(),
            orderItem.getPriceAtPurchase(),
            orderItem.getQuantity()
        );
    }
    
}
