package com.abeer.store.controller;
import java.net.URI;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abeer.store.dto.response.*;
import com.abeer.store.service.ProductService;
import com.abeer.store.dto.request.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;




@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    //GET: Retrieve a paginated catalog of all products
    @GetMapping
    public ResponseEntity<Page<ProductResponse.Full>> getAllProducts(Pageable pageable){
        log.info("Fetching a page of products");
        Page<ProductResponse.Full>products = productService.findAll(pageable);
        return ResponseEntity.ok(products);
    }
    //GET: Retrieve details for a single specific product
   @GetMapping("/{id}")
   public ResponseEntity<ProductResponse.Full> getProductById(@PathVariable Long id){
    log.info("Fetching product ID: {}", id);
    ProductResponse.Full product= productService.findById(id);
    return ResponseEntity.ok(product);
   }
   //POST: Register a completely new product
   @PostMapping
   public ResponseEntity<ProductResponse.Full> createProduct(@Valid @RequestBody CreateProductRequest request){
    log.info("POST /api/v1/products - name: {}", request.productName());
    ProductResponse.Full created = productService.create(request);
    URI location = URI.create("/api/v1/products/"+created.productId());
    return ResponseEntity.created(location).body(created);
   }
   //PUT: Fully overwrite an existing product
   @PutMapping("/{id}")
   public ResponseEntity<ProductResponse.Full> update(
    @PathVariable Long id, @Valid @RequestBody UpdateProductRequest request){
        log.info("PUT /api/v1/products/{}",id);
        return ResponseEntity.ok(productService.update(id,request));
    }
    //PATCH: Update only the price of an existing product
    @PatchMapping("/{id}/price")
    public ResponseEntity<ProductResponse.Full>updatePrice(
        @PathVariable Long id,@Valid @RequestBody UpdatePriceRequest request){
            log.info("PATCH /api/v1/products/{}/price - new price: {}", id, request.currentPrice());
          return ResponseEntity.ok(productService.updatePrice(id,request));
        }
        
    //DELETE: Permanently remove a product
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id){
    log.info("DELETE/api/v1/products/{}",id);
    productService.delete(id);
    return ResponseEntity.noContent().build();
   }
    
}
