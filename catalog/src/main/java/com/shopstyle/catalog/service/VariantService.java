package com.shopstyle.catalog.service;

import com.shopstyle.catalog.model.DTO.VariantDTO;
import com.shopstyle.catalog.model.Variant;

public interface VariantService {
    Variant create(VariantDTO variantDTO);

    Variant update(VariantDTO variantDTO);

    void deleteById(long variant_id);

    Variant findById(long variant_id);
}
