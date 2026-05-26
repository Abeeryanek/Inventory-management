package com.abeer.store.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="Orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotBlank
    @Column(nullable=false)
    private String orderName;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private OrderStatus status;

@Column(nullable = false)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<OrderItem> items = new ArrayList<>();

    //--- Constructors ---
    protected Order() {}

    public Order(String orderName, User user){
        this.orderName = orderName;
        this.user = user;
        this.status = OrderStatus.PENDING;
    }

    // --- DDD Logic ---

    public void markAsShipped() {
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot ship a cancelled order.");
        }
        this.status = OrderStatus.SHIPPED;
    }
    public void addOrderItem(OrderItem newItem){
       if(this.status == OrderStatus.CANCELLED || this.status == OrderStatus.SHIPPED) {
           throw new IllegalStateException("Cannot add items to a cancelled order.");
       }
       this.items.add(newItem);
       newItem.setOrder(this);
        recalculatedTotalPrice();
    }
    public void recalculatedTotalPrice() {
        this.totalPrice= this.items.stream().map(item-> item.getPriceAtPurchase()
        .multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public void cancel(){
        if(this.status==OrderStatus.SHIPPED || 
            this.status==OrderStatus.DELIVERED){
                throw new IllegalStateException(
                    "cannot cancel an order that is already been" +this.status);
            }
             this.status = OrderStatus.CANCELLED;
    }
    //--- Getters ---
    public Long getOrderId() {
        return orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public OrderStatus getOrderStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public List<OrderItem> getItems() {
        return items;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
}
}