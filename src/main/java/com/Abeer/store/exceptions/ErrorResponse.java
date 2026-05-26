package com.abeer.store.exceptions;

public class ErrorResponse extends RuntimeException {
    public ErrorResponse(String error, String message, int status){
        super(message);
    }
}
