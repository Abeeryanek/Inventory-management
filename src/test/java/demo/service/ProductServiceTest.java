package demo.service;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abeer.store.dto.product.CreateProductRequest;
import com.abeer.store.dto.product.ProductResponse;
import com.abeer.store.dto.product.UpdatePriceRequest;
import com.abeer.store.dto.product.UpdateStockQuantityRequest;
import com.abeer.store.entity.Product;
import com.abeer.store.mapper.ProductMapper;
import com.abeer.store.repository.ProductRepository;
import com.abeer.store.service.ProductService;
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Spy
    private ProductMapper productMapper;
    @InjectMocks
    private ProductService productService;

    //arrange
    //act 
    //assert
    @Test
    public void shouldSaveProductAndReturnResponseWhenRequestIsValid(){
        CreateProductRequest request= new CreateProductRequest("Laptop", BigDecimal.valueOf(100), 1);
        Product savedProduct = new Product("Laptop", BigDecimal.valueOf(100), 1);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponse result=productService.create(request);
        
        assertEquals(savedProduct.getCurrentPrice(),result.currentPrice());
        assertEquals(savedProduct.getName(),result.name());
        assertEquals(savedProduct.getStockQuantity(),result.stockQuantity());

        verify(productRepository).save(any(Product.class));
       
    }
    @Test
    public void shouldIncreaseStockWhenQuantityIsPositive(){
        UpdateStockQuantityRequest request= new UpdateStockQuantityRequest(2);
        Product savedProduct = new Product("Laptop", BigDecimal.valueOf(100), 1);
        //i didnt write setter for id here we need id for the method 
        when(productRepository.findById(1L)).thenReturn(Optional.of(savedProduct));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        ProductResponse result=productService.updateStockQuantity(1L,request);

        assertEquals(3,result.stockQuantity());
        verify(productRepository).findById(1L);

    }
    @Test
    public void shouldChangePriceWhenQuantityIsPositive(){
        Product savedProduct= new Product("Laptop", BigDecimal.valueOf(100), 1);
        UpdatePriceRequest request=new UpdatePriceRequest(BigDecimal.valueOf(50));

        when(productRepository.findById(1L)).thenReturn(Optional.of(savedProduct));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        ProductResponse result= productService.updatePrice(1L,request);

        assertEquals(BigDecimal.valueOf(50),result.currentPrice());
        verify(productRepository).findById(1L);
    }
    @Test
    public void shouldBeUnavailable(){
        Product product = new Product("Laptop", BigDecimal.valueOf(100), 1);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
       ProductResponse result= productService.markUnavailable(1L);
        
        assertFalse(result.isAvailable());
        verify(productRepository).findById(1L);
    }


}
