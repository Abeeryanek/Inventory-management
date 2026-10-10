package com.abeer.store.controller;
import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abeer.store.dto.product.CreateProductRequest;
import com.abeer.store.dto.product.ProductResponse;
import com.abeer.store.dto.product.UpdatePriceRequest;
import com.abeer.store.dto.product.UpdateStockQuantityRequest;
import com.abeer.store.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;





@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
 
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable){
        Page<ProductResponse>products = productService.findAll(pageable);
        return ResponseEntity.ok(products);
    }
    
   @GetMapping("/{id}")
   public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
    ProductResponse product= productService.findById(id);
    return ResponseEntity.ok(product);
   }
   
   @PostMapping
   public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request){
    ProductResponse created = productService.create(request);
    URI location = URI.create("/api/products/"+created.id());
    return ResponseEntity.created(location).body(created);
   }
   
   @PatchMapping("/{id}/stockQuantity")
   public ResponseEntity<ProductResponse> updateStockQuantity(
    @PathVariable Long id, @Valid @RequestBody UpdateStockQuantityRequest request){
        return ResponseEntity.ok(productService.updateStockQuantity(id,request));
    }
    
    @PatchMapping("/{id}/price")
    public ResponseEntity<ProductResponse>updatePrice(
        @PathVariable Long id,@Valid @RequestBody UpdatePriceRequest request){
          return ResponseEntity.ok(productService.updatePrice(id,request));
        }
        
    
   @PatchMapping("/{id}")
   public ResponseEntity<ProductResponse> markProductAsUnavailable(@PathVariable Long id){
    return ResponseEntity.ok(productService.markUnavailable(id));
   }
    
}
