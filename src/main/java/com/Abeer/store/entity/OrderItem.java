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

@Entity 
@Table(name="order_items")
public class OrderItem {
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
    @Column(nullable=false)
    private BigDecimal priceAtPurchase;
    @Column(nullable=false)
    private Integer quantity;

  
// ---DDD---
protected OrderItem() {}
  public OrderItem(Order order, Product product, BigDecimal priceAtPurchase, Integer quantity){
        this.order = order;
        this.product = product;
        this.priceAtPurchase = priceAtPurchase;
        this.quantity = quantity;
    }
    
    //--- Getters ---
    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public Product getProduct() {
        return product;
    }
    public Order getOrder() {
        return order;
    }
    public Long getOrderItemId(){
        return orderItemId;
    }
    //---setters---

    public void setOrder(Order order) {
        this.order = order;
    }
    
}
