package com.shopstyle.history.service;

import com.shopstyle.history.entities.DTO.PurchaseDTO;
import com.shopstyle.history.entities.Payment;
import com.shopstyle.history.entities.Product;
import com.shopstyle.history.entities.Purchase;
import com.shopstyle.history.entities.User;
import com.shopstyle.history.repositories.PurchaseRepository;
import com.shopstyle.history.repositories.UserRepository;
import com.shopstyle.history.util.SequenceGeneration;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;


@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SequenceGeneration sequenceGeneration;

    public void save(PurchaseDTO purchaseDTO) {


        Purchase purchase = new ModelMapper().map(purchaseDTO, Purchase.class);

        purchase.setDate(formatDate());
        purchase.setTotal(calculeTotal(purchase.getProducts(), purchase.getPaymentMethod()));

        User user = userRepository.findById(purchase.getUser_id()).get();
        System.out.println();
        purchase.getProducts().forEach(product -> {
            product.setProduct_id(sequenceGeneration.getSequenceNumber(Product.SEQUENCE_NAME));
            mongoTemplate.save(product);
        });

        mongoTemplate.save(purchase);
        mongoTemplate.update(User.class)
                .matching(where("user_id").is(user.getUser_id()))
                .apply(new Update().push("purchases", purchase))
                .first();
        purchaseRepository.save(purchase);

        System.out.println(purchaseRepository.findById(purchase.getPurchase_id()));
    }

    private Double calculeTotal(List<Product> products, Payment payment) {
        double total = 0;
        for (Product product : products) {
            total += product.getQuantity() * product.getPrice();
        }
        double descont = total * payment.getDiscount() / 100;

        return (total - descont);
    }

    private String formatDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public Purchase findByPurchase_Id(Long purchase_id) {
        return purchaseRepository.findById(purchase_id).get();
    }
}
