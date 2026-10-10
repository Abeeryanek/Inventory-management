package com.abeer.store.entity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.abeer.store.enums.OrderStatus;

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
import lombok.Getter;

@Entity
@Table(name="Orders")
@Getter
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

   
    protected Order() {}
    
    public Order(User user){
        if(user==null){
            throw new IllegalArgumentException("user should not be null"

            );
        }
        this.user = user;
        this.status = OrderStatus.PENDING;
    }

    

    public void markAsShipped() {
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot ship a cancelled order.");
        }
        //price will be taken from the konto //TODO 
        this.status = OrderStatus.SHIPPED;
    }
 
    public void cancel(){
        if(this.status==OrderStatus.SHIPPED || 
            this.status==OrderStatus.DELIVERED ||
       this.status==OrderStatus.CANCELLED ){
                throw new IllegalStateException(
                    "cannot cancel an order that is already been" +this.status);
                }
             this.status = OrderStatus.CANCELLED;
        }
    public void addOrderItem(OrderItem orderItem)
    {
        items.add(orderItem);
        this.totalPrice=totalPrice.add(orderItem.getPriceAtPurchase().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

    }
    
}