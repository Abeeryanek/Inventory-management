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
        this.productName = productName;
        this.currentPrice = currentPrice;
        this.stockQuantity = stockQuantity;
        this.lastUpdated = LocalDateTime.now();
    }
    public void reserveStock(Integer stockQuantityRequested){
        if(this.stockQuantity<stockQuantityRequested){
            throw new ProductOutOfStockException("Product out of stock");
        }
        this.stockQuantity-=stockQuantityRequested;
        
    }
    //when cancelling an order this should be called to regain the stock
    public void restoreStock(Integer stockQuantityRequested){
        this.stockQuantity+=stockQuantityRequested;
    }

    public void updatePrice(BigDecimal newPrice)
{
    if(newPrice.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Price must be greater than zero.");
    }
    this.currentPrice = newPrice;
    this.lastUpdated = LocalDateTime.now();
}    
    public void updateDetails(String productName,BigDecimal newPrice,Integer stockQuantity){
        if(newPrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        this.productName=productName;
        this.currentPrice=newPrice;
        this.stockQuantity=stockQuantity;
        this.lastUpdated=LocalDateTime.now();
}

}
