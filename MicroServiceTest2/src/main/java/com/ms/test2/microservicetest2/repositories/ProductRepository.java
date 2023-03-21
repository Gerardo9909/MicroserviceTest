package com.ms.test2.microservicetest2.repositories;

import com.ms.test2.microservicetest2.entities.Category;
import com.ms.test2.microservicetest2.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
