package com.abeer.store.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {
  public record OrderItemResponse(
    Long orderItemId,
    String productName,
    BigDecimal priceAtpurchase,
    Integer quantity)
    {}
  public record Full (
    Long orderId,
    String orderName,
    String orderStatus,
    BigDecimal price,
    String username,
    List<OrderItemResponse> items  
  ) {}
}





