package com.Abeer.store.exceptions;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException (String message){
        super(message);
    }
    
}
