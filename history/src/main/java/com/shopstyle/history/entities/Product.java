package com.shopstyle.history.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;


@AllArgsConstructor
@NoArgsConstructor
@Data
//@Document
public class Product {
    @Transient
    public static final String SEQUENCE_NAME = "product_sequence";
    @Id
    private Long product_id;
    private Long purchase_id;
    private String name;
    private String description;
    private String color;
    private String size;
    private Double price;
    private Long quantity;
}
