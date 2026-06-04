package com.abeer.store.mapper;
import org.springframework.stereotype.Component;

import com.abeer.store.dto.response.ProductResponse;
import com.abeer.store.entity.Product;
@Component
public class ProductMapper {
    public ProductResponse.Full toResponse(Product product){
        return new ProductResponse.Full(
        product.getProductId(),
        product.getProductName(),
        product.getCurrentPrice(),
        product.getStockQuantity(),
        product.getLastUpdated());
    
}
}