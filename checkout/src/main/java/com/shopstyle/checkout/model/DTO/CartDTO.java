package com.shopstyle.checkout.model.DTO;

import com.shopstyle.checkout.model.Purchase;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDTO {
    private Long id;
    private Long variant_id;
    private Long quantity;
    private Purchase purchase;
}
