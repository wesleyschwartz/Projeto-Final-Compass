package com.shopstyle.catalog.controller;

import com.shopstyle.catalog.model.DTO.ProductDTO;
import com.shopstyle.catalog.model.Product;
import com.shopstyle.catalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(productService.create(productDTO)
                        .getProduct_id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{product_id}")
    ResponseEntity<Product> update(@PathVariable long product_id, @RequestBody @Valid ProductDTO productDTO) {
        productDTO.setProduct_id(product_id);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{product_id}").buildAndExpand(productService.update(productDTO).getProduct_id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{product_id}")
    ResponseEntity<Object> delete(@PathVariable long product_id) {
        productService.deleteById(product_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok().body(productService.findAll());
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<Product> findProductById(@PathVariable long product_id) {
        return ResponseEntity.ok().body(productService.findById(product_id));
    }

}
