package com.shopstyle.checkout.toSend.config;

import com.shopstyle.checkout.model.Cart;
import com.shopstyle.checkout.toSend.modelSend.HistoryToSend;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMessages {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Cart cart) {
        rabbitTemplate.convertAndSend(MessageConfig.CART_EXCHANGE, MessageConfig.CART_ROUTINGKEY, cart);
    }

    public void historySendMessage(HistoryToSend historyToSend) {
        rabbitTemplate.convertAndSend(MessageConfig.HISTORY_EXCHANGE, MessageConfig.HISTORY_ROUTINGKEY, historyToSend);
    }


}
