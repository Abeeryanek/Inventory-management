package com.abeer.store.exception;

    public record ErrorResponse(
        String error, 
        String message, 
        int status
    ) {}

