package com.abeer.store.dto.request;
import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
public record UpdatePriceRequest(
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    BigDecimal currentPrice  
)
{}
