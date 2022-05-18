package com.shopstyle.checkout.feign;

import com.shopstyle.checkout.feign.clients.catalogFeignEntities.Product;
import com.shopstyle.checkout.feign.clients.catalogFeignEntities.Variant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
@FeignClient(name = "catalog", url = "http://localhost:8765", path = "/v1")
public interface CatalogFeignClient {
    @GetMapping("/variations/{variant_id}")
    public ResponseEntity<Variant> findVariantById(@PathVariable long variant_id);
    @GetMapping("/products/{product_id}")
    public ResponseEntity<Product> findProductById(@RequestHeader("Authorization") String token, @PathVariable long product_id);
}

