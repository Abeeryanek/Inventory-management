package com.Abeer.store.entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
@Entity
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
  
    //--- DDD ---
    protected Product() {}
  public Product(String productName, BigDecimal currentPrice, Integer stockQuantity, LocalTime lastUpdated) {
        this.productName = productName;
        this.currentPrice = currentPrice;
        this.stockQuantity = stockQuantity;
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
//--- Getters ---
    public Long getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName;
    }
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
}
