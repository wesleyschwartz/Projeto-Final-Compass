package com.shopstyle.catalog.repository;

import com.shopstyle.catalog.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {
    @Query("{ 'product_id' : ?0 }")
    public Product findByProduct_id(long product_id);
}
