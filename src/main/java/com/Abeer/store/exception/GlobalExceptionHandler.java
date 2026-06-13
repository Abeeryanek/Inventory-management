package com.abeer.store.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//custome

@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
    log.warn("Resource not found: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse("NOT_FOUND", ex.getMessage(), 404));
}

@ExceptionHandler(ProductOutOfStockException.class)
public ResponseEntity<ErrorResponse> handleOutOfStock(ProductOutOfStockException ex) {
     log.warn("Out of stock: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new ErrorResponse("OUT_OF_STOCK", ex.getMessage(), 409));
}

//standard java exceptions
@ExceptionHandler(IllegalStateException.class)
public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex){
    log.warn("Business logic state conflict: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(new ErrorResponse("CONFLICT", ex.getMessage(), 409));
   
}

@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<ErrorResponse> handelIllegalArgumentException(IllegalArgumentException ex){
    log.warn("Client provided an invalid argument: {}",ex);
    return ResponseEntity
    .status(HttpStatus.BAD_REQUEST)
    .body(new ErrorResponse("BAD_REQUEST","Invalid argument provided in the request",400) );
}
//validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("Validation failed: {}", errors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("VALIDATION_FAILED", errors, 400));
    }
//catch all
@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleGeneral(Exception ex){
    log.error("Unexpected error occurred", ex);
    return ResponseEntity
    .status(HttpStatus.INTERNAL_SERVER_ERROR)
    .body(new ErrorResponse("INTERNAL_ERROR","Something went wrong",500));
   
}

}