package academy.digitallab.store.shopping.client;

import academy.digitallab.store.shopping.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("product-service")
@RequestMapping("/products")
public interface ProductClient {

    @GetMapping("/{id}")
    ResponseEntity<Product> getProduct(@PathVariable("id") Long productId);

    @GetMapping("/{id}/stock")
    ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam("quantity") Double quantity);
}
