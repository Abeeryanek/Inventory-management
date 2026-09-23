package com.abeer.store.dto.order;

import java.math.BigDecimal;
import java.util.List;
import com.abeer.store.dto.orderItem.OrderItemResponse;

 
  public record OrderResponse (
    Long orderId,
    String orderName,
    String orderStatus,
    BigDecimal price,
    String username,
    List<OrderItemResponse> items  
  ) {}





