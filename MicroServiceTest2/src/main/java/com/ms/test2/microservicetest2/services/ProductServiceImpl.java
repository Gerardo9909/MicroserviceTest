package com.ms.test2.microservicetest2.services;

import com.ms.test2.microservicetest2.entities.Category;
import com.ms.test2.microservicetest2.entities.Product;
import com.ms.test2.microservicetest2.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("Created");
        product.setCreatedAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDb = getProduct(product.getId());
        if (productDb != null) {
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setCategory(product.getCategory());
            productDb.setPrice(product.getPrice());
            return productRepository.save(productDb);
        }
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        Product product = getProduct(id);
        if (product != null) {
            product.setStatus("Deleted");
            return productRepository.save(product);
        }

        return null;
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product product = getProduct(id);

        if (product != null) {
            product.setStock(product.getStock() + quantity);
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }
}
