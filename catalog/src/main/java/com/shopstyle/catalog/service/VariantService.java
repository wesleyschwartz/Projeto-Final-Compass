package com.shopstyle.catalog.service;

import com.shopstyle.catalog.model.DTO.VariantDTO;
import com.shopstyle.catalog.model.Product;
import com.shopstyle.catalog.model.Variant;
import com.shopstyle.catalog.repository.ProductRepository;
import com.shopstyle.catalog.repository.VariantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
@Service
public class VariantService {
    @Autowired
    VariantRepository variantRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MongoOperations mongoOperations;
    @Autowired
    SequenceGeneration sequence;
    @Autowired
    MongoTemplate mongoTemplate;

    public Variant create(VariantDTO variantDTO) {
        Variant variant = new ModelMapper().map(variantDTO, Variant.class);
        variant.setVariant_id(sequence.getSequenceNumber(Variant.SEQUENCE_NAME));
        Product product = productRepository.findByProduct_id(variantDTO.getProduct_id());
        if (product != null) {
            mongoTemplate.save(variant);
            mongoTemplate.update(Product.class)
                    .matching(where("product_id").is(product.getProduct_id()))
                    .apply(new Update().push("variations", variant))
                    .first();
            return variantRepository.save(variant);
        } else throw new RuntimeException("Product not found");
    }

    public Variant findById(long variant_id) {
        Variant variant = variantRepository.findByVariant_id(variant_id);
        if (variant != null) {
            return variant;
        } else throw new RuntimeException("Variant not found");
    }

    public Variant update(VariantDTO variantDTO) {
        Variant variant = new ModelMapper().map(variantDTO, Variant.class);
        variant.setVariant_id(variantDTO.getVariant_id());
        return variantRepository.save(variant);
    }

    public void deleteById(long variant_id) {
        findById(variant_id);
        variantRepository.deleteById(variant_id);
    }
}
