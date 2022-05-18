package com.shopstyle.catalog.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantDTO {
    private Long variant_id;
    @NotNull
    private String color;
    @NotNull
    private String size;
    @NotNull
    private Double price;
    @NotNull
    private Long quantity;
    @NotNull
    private Long product_id;
}
