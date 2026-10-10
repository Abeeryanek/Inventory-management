package com.abeer.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abeer.store.dto.order.CreateOrderRequest;
import com.abeer.store.dto.order.OrderResponse;
import com.abeer.store.dto.orderItem.AddOrderItemRequest;
import com.abeer.store.entity.Order;
import com.abeer.store.entity.OrderItem;
import com.abeer.store.entity.Product;
import com.abeer.store.entity.User;
import com.abeer.store.exception.ResourceNotFoundException;
import com.abeer.store.mapper.OrderMapper;
import com.abeer.store.repository.OrderRepository;
import com.abeer.store.repository.ProductRepository;
import com.abeer.store.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Transactional(readOnly=true)
    public OrderResponse findById(Long id){
       Order order= findOrderOrThrow(id);
       return orderMapper.toResponse(order);
    }
    @Transactional(readOnly=true)
    public Page<OrderResponse> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toResponse);
    }
    @Transactional
    public OrderResponse create(CreateOrderRequest request){
        User user = userRepository.findById(request.userId())
    .orElseThrow(()->new ResourceNotFoundException("User not found: " + request.userId()));
    //id automatic generated right??
        Order order = new Order(user);
        Order saved=orderRepository.save(order); 
        return orderMapper.toResponse(saved);
    }
    @Transactional
    public OrderResponse addOrderItem(Long id,AddOrderItemRequest request){
       Order order=findOrderOrThrow(id);
       Product product = productRepository.findById(request.productId()).orElseThrow(()-> new ResourceNotFoundException("Product not found: " + request.productId()));
       OrderItem orderItem= new OrderItem(order, product, product.getCurrentPrice(), request.quantity());
       product.reserveStock(request.quantity());
       order.addOrderItem(orderItem);
       Order saved = orderRepository.save(order);
       return orderMapper.toResponse(saved);
       
    }
    @Transactional
    public OrderResponse cancel(Long id){
        Order order=findOrderOrThrow(id);
        order.cancel();
        order.getItems().forEach(item->item.getProduct().restoreStock(item.getQuantity()));
        Order saved = orderRepository.save(order);
       return orderMapper.toResponse(saved);
    }

    //private methods
    private Order findOrderOrThrow(Long id){

        Order order=orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found: " + id));
        return  order;
        
    }
}
