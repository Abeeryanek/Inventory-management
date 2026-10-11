package com.abeer.store.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abeer.store.dto.product.CreateProductRequest;
import com.abeer.store.dto.product.ProductResponse;
import com.abeer.store.dto.product.UpdatePriceRequest;
import com.abeer.store.dto.product.UpdateStockQuantityRequest;
import com.abeer.store.entity.Product;
import com.abeer.store.exception.ResourceNotFoundException;
import com.abeer.store.mapper.ProductMapper;
import com.abeer.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class ProductService {
     
      private final ProductMapper productMapper;
      private final ProductRepository productRepository;
      
    @Transactional(readOnly=true)
    public ProductResponse findById(Long id) { 
      Product product=findProductOrThrow(id);
      return productMapper.toResponse(product); }

    @Transactional(readOnly=true)
    public Page<ProductResponse> findAll(Pageable pageable) { 
      return productRepository.findAll(pageable).map(productMapper::toResponse); }
    @Transactional
    public ProductResponse create(CreateProductRequest request) { 
      if(productRepository.existsByProductName(request.name()))
        throw new IllegalArgumentException(
          "product with the name '"+request.name() + "' alreday exists");
      Product product = new Product(request.name(),request.currentPrice(),request.stockQuantity());
      Product saved = productRepository.save(product);
      return productMapper.toResponse(saved);
    }
    @Transactional
    public ProductResponse updateStockQuantity(Long id, UpdateStockQuantityRequest request) { 
      Product product=findProductOrThrow(id);
      product.addStockQuantity(request.stockQuantity());
      Product saved = productRepository.save(product);
      return productMapper.toResponse(saved);
      }
      @Transactional
    public ProductResponse updatePrice(Long id, UpdatePriceRequest request) { 
      Product product=findProductOrThrow(id);
      product.updatePrice(request.currentPrice());
      Product saved = productRepository.save(product);
      return productMapper.toResponse(saved);
     }
     @Transactional  
    public ProductResponse markUnavailable(Long id) {
      Product product=findProductOrThrow(id);
      product.markUnavailable();
      Product saved = productRepository.save(product);
      return productMapper.toResponse(saved);
    }
    
    //private methods 
    private Product findProductOrThrow(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
}
}
