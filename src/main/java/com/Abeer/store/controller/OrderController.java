package com.abeer.store.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abeer.store.dto.order.CreateOrderRequest;
import com.abeer.store.dto.order.OrderResponse;
import com.abeer.store.dto.orderItem.AddOrderItemRequest;
import com.abeer.store.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;





@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
  
 @GetMapping
 public ResponseEntity<Page<OrderResponse>> getAllOrders(Pageable pageable){
    
    Page<OrderResponse> orders= orderService.findAll(pageable);
    return ResponseEntity.ok(orders);
 }
 @GetMapping("/{id}")
 public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id){
    OrderResponse order = orderService.findById(id);
    return ResponseEntity.ok(order);
 }
@PostMapping
public ResponseEntity<OrderResponse> createOrder( @Valid @RequestBody  CreateOrderRequest request){
        
        OrderResponse created= orderService.create(request);
        URI location = URI.create("/api/orders"+created.id());
        return ResponseEntity.created(location).body(created);
}

@PutMapping("/{id}")
public ResponseEntity<OrderResponse> addOrderItem(@PathVariable Long id, 
    @Valid @RequestBody AddOrderItemRequest request){
        OrderResponse add = orderService.addOrderItem(id,request);
        return ResponseEntity.ok(add);
        
    }

@PatchMapping("/{id}")
public ResponseEntity<OrderResponse> markAsUnavaila(@PathVariable Long id){
    
    return ResponseEntity.ok(orderService.cancel(id));
}

}
