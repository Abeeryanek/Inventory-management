package com.abeer.store.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.abeer.store.exception.ProductOutOfStockException;

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
    private Long productId;

    @NotBlank
    @Column(nullable=false)
    private String productName;

    @Column(nullable=false)
    private BigDecimal currentPrice;
    @Column(nullable=false)
    private LocalDateTime lastUpdated;
    @Column(nullable=false)
    private Integer stockQuantity;

    protected Product() {}
  public Product(String productName, BigDecimal currentPrice, Integer stockQuantity) {
    if(productName==null || productName.isBlank()){
        throw new IllegalArgumentException("Product name must not be blank.");
    }
    if(currentPrice==null || currentPrice.compareTo(BigDecimal.ZERO)<=0){
        throw new IllegalArgumentException("price must be greater than zero");
    }
    if(stockQuantity==null||stockQuantity< 0){
        throw new IllegalArgumentException("stock quantity cannot be negative or empty");
    }
        this.productName = productName;
        this.currentPrice = currentPrice;
        this.stockQuantity = stockQuantity;
        this.lastUpdated = LocalDateTime.now();
}

    public void reserveStock(Integer stockQuantity){
         if(stockQuantity==null||stockQuantity<0){
            throw new IllegalArgumentException("Stock quantity requested must be greater than zero.");
        }
        if(this.stockQuantity<stockQuantity){
            throw new ProductOutOfStockException("Product out of stock");
        }
        
        this.stockQuantity-=stockQuantity;
        this.lastUpdated = LocalDateTime.now();
   
    }
    //when cancelling an order this should be called to regain the stock
    public void restoreStock(Integer stockQuantity){
        if(stockQuantity== null || stockQuantity<0){
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
    

}
