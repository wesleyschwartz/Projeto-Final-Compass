package com.shopstyle.history.repositories;

import com.shopstyle.history.entities.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends MongoRepository<Purchase, Long> {
}
