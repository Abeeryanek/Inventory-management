package com.abeer.store.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest(
    @NotBlank(message="Product name is required")
    @Size(max=100,message="Name must be under 100 characters")
    String orderName,
    @NotNull(message="User ID is required")
    Long userId
)
{}
