package com.shopstyle.history.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long purchase_id;

    private String name;
    private String description;
    private String color;
    private String size;
    private Double price;
    private Long quantity;
}
