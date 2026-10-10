package com.abeer.store.dto.product;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record UpdateStockQuantityRequest (
    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock cannot be negative")
    int stockQuantity
)
{}

