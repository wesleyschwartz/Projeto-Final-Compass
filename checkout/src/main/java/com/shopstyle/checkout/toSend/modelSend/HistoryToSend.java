package com.shopstyle.checkout.toSend.modelSend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor

@ToString
public class HistoryToSend {
    private UserToSend userDTO;
    private PurchaseToSend purchaseDTO;
    private List<ProductToSend> productDTOS;

}
