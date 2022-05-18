package com.shopstyle.checkout.feign.clients.catalogFeignEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Variant {
    private Long variant_id;
    private String color;
    private String size;
    private Double price;
    private Long quantity;
    private Long product_id;
}
