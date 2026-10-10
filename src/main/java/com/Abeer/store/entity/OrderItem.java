package com.abeer.store.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter  
@Getter
@Table(name="order_items")
public class OrderItem {
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
    @Column(nullable=false)
    private BigDecimal priceAtPurchase;
    @Column(nullable=false)
    private int quantity;

  

protected OrderItem() {}
  public OrderItem(Order order, Product product, BigDecimal priceAtPurchase, Integer quantity){
    if(order==null){
        throw new IllegalArgumentException("");
    }
    if(product==null){
        throw new IllegalArgumentException("");
    }
    if(priceAtPurchase.compareTo(BigDecimal.ZERO)<0){
        throw new IllegalArgumentException("");
    }
        this.order = order;
        this.product = product;
        this.priceAtPurchase = priceAtPurchase;
        this.quantity = quantity;
    }
    
}
