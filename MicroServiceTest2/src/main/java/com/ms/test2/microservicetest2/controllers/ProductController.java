package com.ms.test2.microservicetest2.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.test2.microservicetest2.entities.Category;
import com.ms.test2.microservicetest2.entities.Product;
import com.ms.test2.microservicetest2.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getProducts(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        List<Product> productList;
        if (null == categoryId) {
            productList = productService.listAllProducts();
        } else {
            productList = productService.findByCategory(Category.builder().id(categoryId).build());
        }
        return productList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long productId) {
        Product product = productService.getProduct(productId);
        return product == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product productCreated = productService.createProduct(product);
        return productCreated == null ? ResponseEntity.badRequest().build() : ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        product.setId(id);
        Product productUpdated = productService.updateProduct(product);
        return productUpdated == null ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(productUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        Product productDeleted = productService.deleteProduct(id);
        return productDeleted == null ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(productDeleted);
    }

        @GetMapping("/{id}/stock")
        public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam("quantity") Double quantity) {
            Product productUpdated = productService.updateStock(id, quantity);
            return productUpdated == null ? ResponseEntity.notFound().build() : ResponseEntity.status(HttpStatus.OK).body(productUpdated);
        }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errorMessages = result.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return error;
                }).toList();

        ErrorMessage errorMessage = ErrorMessage.builder().code("01").messages(errorMessages).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonString;
    }
}
