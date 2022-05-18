package com.shopstyle.catalog.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Transient
    public static final String SEQUENCE_NAME = "category_sequence";
    @Id
    private Long category_id;
    private String name;
    private Boolean active;
    @DocumentReference
    private List<Product> productList= new ArrayList<>();
}
