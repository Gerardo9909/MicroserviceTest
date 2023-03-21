package com.ms.test2.microservicetest2;

import com.ms.test2.microservicetest2.entities.Category;
import com.ms.test2.microservicetest2.entities.Product;
import com.ms.test2.microservicetest2.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_thenResultProductList(){
        Product product01 = Product.builder()
                .name("Computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(10.0)
                .price(124.0)
                .status("Created")
                .createdAt(new Date())
                .build();

        productRepository.save(product01);

        List<Product> products = productRepository.findByCategory(product01.getCategory());
        Assertions.assertEquals(2, products.size());
    }
}
