package com.abeer.store.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record ProductResponse(
        Long productId,
        String productName,
        BigDecimal currentPrice,
        Integer stockQuantity,
        LocalDateTime lastUpdated
    ) {}

