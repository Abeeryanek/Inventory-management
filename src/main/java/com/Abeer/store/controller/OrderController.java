package com.abeer.store.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.abeer.store.dto.response.OrderResponse;
import com.abeer.store.service.OrderService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.abeer.store.dto.request.AddOrderItemRequest;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

import com.abeer.store.dto.request.CreateOrderRequest;




@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderservice;
  
 @GetMapping
 public ResponseEntity<Page<OrderResponse.Full>> getAllOrders(Pageable pageable){
    log.info("fetching orders");
    Page<OrderResponse.Full> orders= orderservice.findAll(pageable);
    return ResponseEntity.ok(orders);
 }
 @GetMapping("/{id}")
 public ResponseEntity<OrderResponse.Full> getOrderById(@PathVariable Long id){
    log.info("Fetching order id :{}", id);
    OrderResponse.Full order = orderservice.findById(id);
    return ResponseEntity.ok(order);
 }
@PostMapping
public ResponseEntity<OrderResponse.Full> createOrder( @Valid @RequestBody  CreateOrderRequest request){
        log.info("POST /api/v1/orders - name {}",request.orderName());
        OrderResponse.Full created= orderservice.create(request);
        URI location = URI.create("/api/v1/orders"+created.orderId());
        return ResponseEntity.created(location).body(created);
}

@PutMapping("/{id}")
public ResponseEntity<OrderResponse.Full> addOrderItem(@PathVariable Long id, 
    @Valid @RequestBody AddOrderItemRequest request){
        log.info("PUT /api/v1/orders/{}",id);
        OrderResponse.Full add = orderservice.addOrderItem(id,request);
        return ResponseEntity.ok(add);
        
    }

@DeleteMapping("/{id}")
public ResponseEntity<Void> delete (@PathVariable Long id){
    log.info("delete /api/v1/orders/{}",id);
    orderservice.delete(id);
    return ResponseEntity.noContent().build();
}

}
