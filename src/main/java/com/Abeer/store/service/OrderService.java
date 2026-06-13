package com.abeer.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abeer.store.dto.request.AddOrderItemRequest;
import com.abeer.store.dto.request.CreateOrderRequest;
import com.abeer.store.dto.response.OrderResponse;
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
    public OrderResponse.Full findById(Long id){
       Order order= findOrderOrThrow(id);
       return orderMapper.toResponse(order);
    }
    @Transactional(readOnly=true)
    public Page<OrderResponse.Full> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toResponse);
    }
    @Transactional
    public OrderResponse.Full create(CreateOrderRequest request){
        User user = userRepository.findById(request.userId())
    .orElseThrow(()->new ResourceNotFoundException("User not found: " + request.userId()));
        Order order = new Order(request.orderName(), user);
        Order saved=orderRepository.save(order); 
        return orderMapper.toResponse(saved);
    }
    @Transactional
    public OrderResponse.Full addOrderItem(Long id,AddOrderItemRequest request){
       Order order=findOrderOrThrow(id);
       Product product = productRepository.findById(request.productId()).orElseThrow(()-> new ResourceNotFoundException("Product not found: " + request.productId()));
       OrderItem orderItem= new OrderItem(order, product, product.getCurrentPrice(), request.quantity());
       order.addOrderItem(orderItem);
       Order saved = orderRepository.save(order);
       return orderMapper.toResponse(saved);
       
    }
    @Transactional
    public void delete(Long id){
        Order order=findOrderOrThrow(id);
        order.cancel();
        orderRepository.save(order);}

    //private methods
    private Order findOrderOrThrow(Long id){
        Order order=orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found: " + id));

        return  order;
    }
}
