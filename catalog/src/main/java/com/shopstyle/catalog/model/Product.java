package com.shopstyle.catalog.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Transient
    public static final String SEQUENCE_NAME = "product_sequence";
    @Id
    private Long product_id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Boolean active;
    @NotNull
    private List<Long> category_ids;
    @DocumentReference
    private List<Variant> variations = new ArrayList<>();

}
