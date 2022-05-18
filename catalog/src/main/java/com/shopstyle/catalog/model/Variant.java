package com.shopstyle.catalog.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Variant {
    @Transient
    public static final String SEQUENCE_NAME = "variant_id";
    @Id
    private Long variant_id;
    private String color;
    private String size;
    private Double price;
    private Long quantity;
    private Long product_id;
}
