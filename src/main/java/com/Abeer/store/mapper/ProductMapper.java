package com.abeer.store.mapper;
import org.springframework.stereotype.Component;

import com.abeer.store.dto.product.ProductResponse;
import com.abeer.store.entity.Product;
@Component
public class ProductMapper {
    public ProductResponse toResponse(Product product){
        return new ProductResponse(
        product.getProductId(),
        product.getProductName(),
        product.getCurrentPrice(),
        product.getStockQuantity(),
        product.getLastUpdated());
    
}
}