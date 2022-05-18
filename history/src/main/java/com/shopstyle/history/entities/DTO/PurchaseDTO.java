package com.shopstyle.history.entities.DTO;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
public class PurchaseDTO {
    private Long purchase_id;
    private Long user_id;
    private PaymentDTO paymentMethod;
    private List<ProductDTO> products = new ArrayList<>();
}
