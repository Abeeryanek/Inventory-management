package com.abeer.store.exceptions;

    public record ErrorResponse(
        String error, 
        String message, 
        int status
    ) {}

