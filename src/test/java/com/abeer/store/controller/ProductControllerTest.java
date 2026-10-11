package com.abeer.store.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.abeer.store.dto.product.*;
import com.abeer.store.service.ProductService;

import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false) 
public class ProductControllerTest {
    @Autowired 
    private MockMvc mockMvc;

    @MockitoBean 
    private ProductService productService;

    private ProductResponse aResponse(){
        return new ProductResponse(
        1L,"Laptop", BigDecimal.valueOf(100), 5,LocalDateTime.of(2025, 1, 15, 12, 0), true);
    }
    @Test 
    void shouldReturn200WithProductWhenIdExists() throws Exception{
        when(productService.findById(1L)).thenReturn(aResponse());
        mockMvc.perform(get("/api/products/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Laptop"));
    }
    @Test
    void shouldReturn201WhenRequestIsValid() throws Exception{
        when(productService.create(any(CreateProductRequest.class)))
        .thenReturn(aResponse());
        mockMvc.perform(post("/api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                {"name": "Laptop", "currentPrice": 100, "stockQuantity": 5}
                """))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/api/products/1"))
        .andExpect(jsonPath("$.name").value("Laptop"));
    }
    @Test
    void  shouldReturn404WhenRequestIsValid() throws Exception {
        when(productService.updateStockQuantity(eq(1L), any(UpdateStockQuantityRequest.class)))
        .thenReturn(aResponse());
        mockMvc.perform(patch("/api/products/1/stockQuantity")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                {"stockQuantity": 5}
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Laptop"));
    }
    @Test
    void  shouldReturn400WhenRequestIsValid() throws Exception {
        when(productService.updatePrice(eq(1L),any(UpdatePriceRequest.class)))
        .thenReturn(aResponse());
        mockMvc.perform(patch("/api/products/1/price")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                {"currentPrice": 100}
                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void shouldReturnInactive() throws Exception{
        when(productService.markUnavailable(eq(1L))).thenReturn(aResponse());
        mockMvc.perform(patch("/api/products/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isAvailable").exists());

        verify(productService).markUnavailable(1L);
    }
    
}
