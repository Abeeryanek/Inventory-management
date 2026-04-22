package com.Abeer.store.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(IllegalStateException.class)
public ResponseEntity<Map<String, Object>> handleIllegalStateException(IllegalStateException ex){
Map<String,Object> errorBody= new HashMap<>();
errorBody.put("status",HttpStatus.BAD_REQUEST.value() );
errorBody.put("error", "Bad Request - State Error");
errorBody.put("message", ex.getMessage());
return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    
}

@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<Map<String, Object>> handelIllegalArgumentException(IllegalArgumentException ex){
    Map<String, Object> errorBody= new HashMap<>();
    errorBody.put("status", HttpStatus.BAD_REQUEST.value());
    errorBody.put("error", "Bad Request - Invalid Argument");
    errorBody.put("message", ex.getMessage());
    return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
}

@ExceptionHandler(Exception.class)
public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex){
    Map<String, Object> errorBody= new HashMap<>();
    errorBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    errorBody.put("error", "Internal Server Error");
    errorBody.put("message", ex.getMessage());
    return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
}

}