package com.shopstyle.checkout.service;

import com.shopstyle.checkout.model.Cart;
import com.shopstyle.checkout.respositories.CartRepository;
import com.shopstyle.checkout.toSend.config.SendMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private SendMessages cartSendMessage;

    public void create(Cart cart) {
        cartRepository.save(cart);
    }

    public void cartSendMessage(Cart cart) {
        cartSendMessage.sendMessage(cart);
    }
}
