package com.shopstyle.checkout.feign.clients.catalogFeignEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long product_id;
    private String name;
    private String description;
    private Boolean active;
    private List<Long> category_ids;
    private List<Variant> variations = new ArrayList<>();

}
