package demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.abeer.store.controller.ProductController;
import com.abeer.store.service.ProductService;
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired 
    private MockMvc mockMvc;

    @MockitoBean 
    private ProductService productService;

    @Test 
    void shouldReturn200WithProductWhenIdExists() throws Exception{
        
    }

    
}
