package com.shopstyle.checkout.toSend;

import com.shopstyle.checkout.feign.CatalogFeignClient;
import com.shopstyle.checkout.feign.UserFeignClient;
import com.shopstyle.checkout.feign.clients.catalogFeignEntities.Product;
import com.shopstyle.checkout.feign.clients.catalogFeignEntities.Variant;
import com.shopstyle.checkout.feign.clients.userFeignEntities.TokenDTO;
import com.shopstyle.checkout.feign.clients.userFeignEntities.User;
import com.shopstyle.checkout.feign.clients.userFeignEntities.UserLogin;
import com.shopstyle.checkout.model.Cart;
import com.shopstyle.checkout.model.Purchase;
import com.shopstyle.checkout.respositories.CartRepository;
import com.shopstyle.checkout.service.PaymentService;
import com.shopstyle.checkout.toSend.config.SendMessages;
import com.shopstyle.checkout.toSend.modelSend.HistoryToSend;
import com.shopstyle.checkout.toSend.modelSend.ProductToSend;
import com.shopstyle.checkout.toSend.modelSend.PurchaseToSend;
import com.shopstyle.checkout.toSend.modelSend.UserToSend;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private SendMessages sendMessages;
    @Autowired
    private CatalogFeignClient catalogFeignClient;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserFeignClient userFeignClient;

    public UserToSend userToSend(User user) {
        UserToSend map = new ModelMapper().map(user, UserToSend.class);
        return map;
    }

    public PurchaseToSend purchaseToSend(Purchase purchase) {
        PurchaseToSend purchaseToSend = new ModelMapper().map(purchase, PurchaseToSend.class);
        purchaseToSend.setPaymentMethod(paymentService.findById(purchase.getPayment_id()));
        return purchaseToSend;
    }


    public void toSend(Purchase purchase, User user) {
        UserToSend userToSend = userToSend(user);
        userToSend.setUser_id(purchase.getUser_id());
        HistoryToSend historyToSend = new HistoryToSend(userToSend, purchaseToSend(purchase), getProductsToSend(purchase.getPurchase_id(),purchase.getCart()));
        sendMessages.historySendMessage(historyToSend);
    }

    private List<ProductToSend> getProductsToSend(long purchase_id,List<Cart> carts) {
        List<ProductToSend> productToSends = new ArrayList<>();

        carts.forEach(cart -> {
            Variant variant = catalogFeignClient.findVariantById(cart.getVariant_id()).getBody();
            Product product = catalogFeignClient.findProductById(tokenFeignAcess(), variant.getProduct_id()).getBody();
            productToSends.add(new ProductToSend(purchase_id,product.getName(), product.getDescription(),
                    variant.getColor(), variant.getSize(), variant.getPrice(), cart.getQuantity()));
        });

        return productToSends;
    }

    private String tokenFeignAcess() {
        TokenDTO tokenDTO = userFeignClient.login(new UserLogin("wesley@email.br", "12345")).getBody();
        return tokenDTO.getType() + " " + tokenDTO.getToken();
    }

}
