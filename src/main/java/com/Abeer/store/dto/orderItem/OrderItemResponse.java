package com.abeer.store.dto.orderItem;

import java.math.BigDecimal;

    public record OrderItemResponse(
    Long id,
    String productName,
    BigDecimal priceAtpurchase,
    Integer quantity)
    {}

