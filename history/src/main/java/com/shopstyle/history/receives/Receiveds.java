package com.shopstyle.history.receives;


import com.shopstyle.history.config.ReceiveConfig;
import com.shopstyle.history.entities.DTO.HistoryDTO;
import com.shopstyle.history.entities.DTO.PurchaseDTO;
import com.shopstyle.history.entities.DTO.UserDTO;
import com.shopstyle.history.service.PurchaseService;
import com.shopstyle.history.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Receiveds {
    @Autowired
    private UserService userService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    private PurchaseService purchaseService;
  /*  @Autowired
    private ProductService productService;
*/
    @RabbitListener(queues = ReceiveConfig.HISTORY_QUEUE)
    public void receiveUser(@Payload HistoryDTO historyDTO) {
        UserDTO userDTO = historyDTO.getUserDTO();
        if (!userService.existById(userDTO.getUser_id())) {
            userService.save(historyDTO.getUserDTO());
        }
        PurchaseDTO purchaseDTO = historyDTO.getPurchaseDTO();
        purchaseDTO.setProducts(historyDTO.getProductDTOS());
        purchaseService.save(purchaseDTO);

/*
        List<ProductDTO> productDTOS = historyDTO.getProductDTOS();
        productService.save(productDTOS, historyDTO.getPurchaseDTO().getPurchase_id());
*/
    }
}



