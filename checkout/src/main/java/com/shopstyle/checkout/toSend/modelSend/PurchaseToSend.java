package com.shopstyle.checkout.toSend.modelSend;

import com.shopstyle.checkout.model.DTO.PaymentDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseToSend {
    private Long purchase_id;
    private Long user_id;
    private PaymentDTO paymentMethod;
}
