package com.ms.test2.microservicetest2.services;

import com.ms.test2.microservicetest2.entities.Category;
import com.ms.test2.microservicetest2.entities.Product;
import com.ms.test2.microservicetest2.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
        Product computer = Product.builder()
                .id(1L)
                .name("Computer")
                .category(Category.builder().id(1L).build())
                .stock(10.0)
                .price(5.0)
                .build();

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(computer));
        Mockito.when(productRepository.save(computer)).thenReturn(computer);
    }

    @Test
    public void whenValidGetId_ThenReturnProduct() {
        Product product = productService.getProduct(1L);
        Assertions.assertNotNull(product);
    }

    @Test
    public void whenValidUpdateSetStock_ThenReturnNewStock() {
        Product product = productService.updateStock(1L, 5.0);
        Assertions.assertEquals(15.0, product.getStock());
    }

}