package com.ms.test2.microservicetest2.services;

import com.ms.test2.microservicetest2.entities.Category;
import com.ms.test2.microservicetest2.entities.Product;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ProductService {

    List<Product> listAllProducts();
    Product getProduct(Long id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    Product deleteProduct(Long id);
    Product updateStock(Long id, Double quantity);
    List<Product> findByCategory(Category category);

}
