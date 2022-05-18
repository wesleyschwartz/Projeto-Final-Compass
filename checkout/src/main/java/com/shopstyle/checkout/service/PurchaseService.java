package com.shopstyle.checkout.service;

import com.shopstyle.checkout.feign.CatalogFeignClient;
import com.shopstyle.checkout.feign.UserFeignClient;
import com.shopstyle.checkout.feign.clients.userFeignEntities.TokenDTO;
import com.shopstyle.checkout.feign.clients.catalogFeignEntities.Product;
import com.shopstyle.checkout.feign.clients.catalogFeignEntities.Variant;
import com.shopstyle.checkout.feign.clients.userFeignEntities.User;
import com.shopstyle.checkout.feign.clients.userFeignEntities.UserLogin;
import com.shopstyle.checkout.model.Cart;
import com.shopstyle.checkout.model.DTO.PaymentDTO;
import com.shopstyle.checkout.model.Purchase;
import com.shopstyle.checkout.respositories.PurchaseRepository;
import com.shopstyle.checkout.toSend.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private CatalogFeignClient catalogFeignClient;

    public Purchase create(Purchase purchase) {
        //if (!feignClientExist) createUserFeignAcess();
        try {
            User user = userFeignClient.getById(tokenFeignAcess(), purchase.getUser_id()).getBody();

            if (user.getActive()) {
                PaymentDTO paymentDTO = paymentService.findById(purchase.getPayment_id());

                purchase.getCart().forEach(this::checkQuantityVariantAndProductActive);

                if (paymentDTO.getStatus()) {
                    purchase.getCart().forEach(cart -> {
                        cartService.create(cart);
                        cartService.cartSendMessage(cart);
                    });
                    purchaseRepository.save(purchase);
                }
                historyService.toSend(purchase, user);
                return purchase;
            } else throw new RuntimeException("User not active");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void checkQuantityVariantAndProductActive(Cart cart) {
        try {
            Variant variant = catalogFeignClient.findVariantById(cart.getVariant_id()).getBody();
            if (variant.getQuantity() < cart.getQuantity()) {
                throw new RuntimeException("variant: " + variant.getVariant_id() + " without enough stock, in stock: " + variant.getQuantity());
            }
            Product product = catalogFeignClient.findProductById(tokenFeignAcess(), variant.getProduct_id()).getBody();
            if (!product.getActive()) {
                throw new RuntimeException("product not active");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String tokenFeignAcess() {
        TokenDTO tokenDTO = userFeignClient.login(new UserLogin("wesley@email.br", "12345")).getBody();
        //userFeignClient.login(new UserLogin("feign@client.com", "feignClient")).getBody();
        return tokenDTO.getType() + " " + tokenDTO.getToken();
    }

    /* private static boolean feignClientExist = false;
        private void createUserFeignAcess() {

         userFeignClient.create(new User(null, "feign", "feign", Sex.Masculino, "000.000.000-35", "22/04/1995", "feign@client.com", "feignClient", true));
         feignClientExist = true;
     }
     */
}
