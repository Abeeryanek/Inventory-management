package com.abeer.store.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abeer.store.dto.request.CreateProductRequest;
import com.abeer.store.dto.request.UpdatePriceRequest;
import com.abeer.store.dto.request.UpdateProductRequest;
import com.abeer.store.dto.response.ProductResponse;
import com.abeer.store.entity.Product; 
import com.abeer.store.exceptions.ResourceNotFoundException;
import com.abeer.store.mapper.ProductMapper;
import com.abeer.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {
      private final ProductMapper productMapper;
      private final ProductRepository productRepository;
    @Transactional(readOnly=true)
    public ProductResponse.Full findById(Long id) { 
      Product product=findProductOrThrow(id);
      return productMapper.toResponse(product); }

    @Transactional(readOnly=true)
    public Page<ProductResponse.Full> findAll(Pageable pageable) { 
      return productRepository.findAll(pageable).map(productMapper::toResponse); }
    @Transactional
    public ProductResponse.Full create(CreateProductRequest request) { 
      if(productRepository.existsByProductName(request.productName()))
        throw new IllegalArgumentException(
          "product with the name '"+request.productName() + "' alreday exists");
      Product product= new Product(request.productName(),request.currentPrice(),request.stockQuantity());
      Product saved = productRepository.save(product);
      return productMapper.toResponse(saved);
    }
    @Transactional
    public ProductResponse.Full update(Long id, UpdateProductRequest request) { 
      Product product=findProductOrThrow(id);
      product.updateDetails(request.productName(), request.currentPrice(), request.stockQuantity());
      Product saved = productRepository.save(product);
      return productMapper.toResponse(saved);
      }
      @Transactional
    public ProductResponse.Full updatePrice(Long id, UpdatePriceRequest request) { 
      Product product=findProductOrThrow(id);
      product.updatePrice(request.currentPrice());
      Product saved = productRepository.save(product);
      return productMapper.toResponse(saved);
     }
     @Transactional
    public void delete(Long id) {
      Product product=findProductOrThrow(id);
      productRepository.delete(product);
    }
    
    //private methods 
    private Product findProductOrThrow(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
}
}
