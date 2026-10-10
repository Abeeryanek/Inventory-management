package com.abeer.store.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record ProductResponse(
        Long id,
        String name,
        BigDecimal currentPrice,
        int stockQuantity,
        LocalDateTime lastUpdated,
        boolean isAvailable
    ) {}

