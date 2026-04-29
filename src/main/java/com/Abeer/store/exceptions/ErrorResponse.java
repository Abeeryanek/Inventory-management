package com.abeer.store.exceptions;

public class ErrorResponse extends RuntimeException {
    public ErrorResponse(String message){
        super(message);
    }
}
