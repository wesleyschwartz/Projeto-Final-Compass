package com.shopstyle.catalog.service;

import com.shopstyle.catalog.model.DTO.ProductDTO;
import com.shopstyle.catalog.model.Product;

import java.util.List;

public interface  ProductService {

    Product create(ProductDTO productDTO);

    Product findById(long product_id);

    Product update(ProductDTO productDTO);

    void deleteById(long product_id);

    List<Product> findAll();
}
