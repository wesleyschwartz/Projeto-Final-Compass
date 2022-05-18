package com.shopstyle.catalog.repository;

import com.shopstyle.catalog.model.Variant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends MongoRepository<Variant, Long> {
    @Query("{ 'variant_id' : ?0 }")
    public Variant findByVariant_id(long product_id);

}
