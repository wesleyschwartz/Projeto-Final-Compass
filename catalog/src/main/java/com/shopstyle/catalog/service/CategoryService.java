package com.shopstyle.catalog.service;

import com.shopstyle.catalog.model.Category;
import com.shopstyle.catalog.model.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {

    Category create(CategoryDTO categoryDTO);

    List<CategoryDTO> findAll();

    Category findById(long category_id);

    Category update(CategoryDTO categoryDTO);

    void deleteById(long category_id);
}
