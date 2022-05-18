package com.shopstyle.catalog.config;

import com.shopstyle.catalog.model.DTO.CartDTO;
import com.shopstyle.catalog.model.Product;
import com.shopstyle.catalog.model.Variant;
import com.shopstyle.catalog.repository.ProductRepository;
import com.shopstyle.catalog.repository.VariantRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CartReceiveMessage {

    @Autowired
    private VariantRepository variantRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = MessageConfig.CART_QUEUE)
    public void receive(@Payload CartDTO cartDTO) {
        Variant variant = variantRepository.findByVariant_id(cartDTO.getVariant_id());
        Product product = productRepository.findByProduct_id(variant.getProduct_id());
        if (product.getActive()) {
            long stock = variant.getQuantity() - cartDTO.getQuantity();
            variant.setQuantity(stock);
            variantRepository.save(variant);
        }
    }
}







