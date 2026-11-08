package com.abeer.store.dto.user;
import com.abeer.store.enums.Role;
import jakarta.validation.constraints.NotBlank;

public record UserResponse(
    @NotBlank Long id,
    @NotBlank String username,
    Role role
)
{}