package com.abeer.store.dto.order;

import java.math.BigDecimal;
import java.util.List;

import com.abeer.store.dto.orderItem.OrderItemResponse;

 
  public record OrderResponse (
    Long id,
    String status,
    BigDecimal price,
    String username,
    List<OrderItemResponse> items  
  ) {}





