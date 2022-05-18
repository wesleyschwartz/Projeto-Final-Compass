package com.shopstyle.history.entities.toshow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductShow {
    private String name;
    private String description;
    private String color;
    private String size;
    private Double price;
    private Long quantity;
}
