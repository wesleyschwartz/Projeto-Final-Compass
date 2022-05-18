/*package com.shopstyle.history.service;

import com.shopstyle.history.entities.DTO.ProductDTO;
import com.shopstyle.history.entities.Product;
import com.shopstyle.history.entities.Purchase;
import com.shopstyle.history.repositories.ProductRepository;
import com.shopstyle.history.repositories.PurchaseRepository;
import com.shopstyle.history.util.SequenceGeneration;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class ProductService {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SequenceGeneration sequenceGeneration;

    public void save(List<ProductDTO> productDTOS, Long purchase_id) {
        Purchase purchase = purchaseService.findByPurchase_Id(purchase_id);

    for (ProductDTO productDTO : productDTOS) {
            Product product = new ModelMapper().map(productDTO, Product.class);
            product.setProduct_id(sequenceGeneration.getSequenceNumber(Product.SEQUENCE_NAME));
            product.setPurchase_id(purchase_id);

            mongoTemplate.save(product);
            mongoTemplate.update(Purchase.class)
                    .matching(where("purchase_id").is(purchase_id))
                    .apply(new Update().push("products", product))
                    .first();

            productRepository.save(product);
        }


    }
}


*/