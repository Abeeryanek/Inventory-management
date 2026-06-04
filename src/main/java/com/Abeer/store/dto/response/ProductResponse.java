package com.abeer.store.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponse {
    public record full (
        Long productId,
        String productName,
        BigDecimal currentPrice,
        Integer stockQuantity,
        LocalDateTime lastUpdated
    ) {}
}
