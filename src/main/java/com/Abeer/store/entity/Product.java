package com.abeer.store.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Entity
@Getter 
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private BigDecimal currentPrice;
    @Column(nullable=false)
    private LocalDateTime lastUpdated;
    @Column(nullable=false)
    private int stockQuantity;
    @Column(nullable=false)
    private boolean available;

    // @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval=true)
    // private List<OrderItem> items=new ArrayList<>();
    protected Product() {}
  public Product(String name, BigDecimal currentPrice, int stockQuantity) {
    if(name==null || name.isBlank()){
        throw new IllegalArgumentException("Product name must not be blank.");
    }
    if(currentPrice==null || currentPrice.compareTo(BigDecimal.ZERO)<=0){
        throw new IllegalArgumentException("price must be greater than zero");
    }
    if(stockQuantity< 0){
        throw new IllegalArgumentException("stock quantity cannot be negative or empty");
    }
        this.name = name;
        this.currentPrice = currentPrice;
        this.stockQuantity = stockQuantity;
        this.lastUpdated = LocalDateTime.now();
        this.available=true;
}

    public void reserveStock(int quantity){
        
         if(quantity<0){
            throw new IllegalArgumentException("Stock quantity requested must be greater than zero."

            );
        }
         if(this.stockQuantity<quantity){
            throw new IllegalArgumentException("Product out of stock"
                
            );
        }
        this.stockQuantity-=quantity;
        this.lastUpdated = LocalDateTime.now();
   
    }
    //when cancelling an order this should be called to regain the stock
    public void restoreStock(int stockQuantity){
        if(stockQuantity<0){
            throw new IllegalArgumentException("stock cannot be negative");
        }
        this.stockQuantity+=stockQuantity;
        this.lastUpdated = LocalDateTime.now();
    }

    public void updatePrice(BigDecimal newPrice)
{
    if(newPrice.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Price must be greater than zero.");
    }
    this.currentPrice = newPrice;
    this.lastUpdated = LocalDateTime.now();
}    
    public void addStockQuantity(int stockQuantity)
    {
        if(stockQuantity<=0){
            throw new IllegalArgumentException("stock quantity must be greater than zero.");
        }
        this.stockQuantity+=stockQuantity;
    }
    public void markUnavailable(){
        this.available=false;
    }

}