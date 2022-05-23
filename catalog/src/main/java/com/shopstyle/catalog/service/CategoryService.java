package com.shopstyle.catalog.service;

import com.shopstyle.catalog.model.Category;
import com.shopstyle.catalog.model.DTO.CategoryDTO;
import com.shopstyle.catalog.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SequenceGeneration sequence;

    public Category create(CategoryDTO categoryDTO) {
        Category category = new ModelMapper().map(categoryDTO, Category.class);
        category.setCategory_id(sequence.getSequenceNumber(Category.SEQUENCE_NAME));
        return categoryRepository.save(category);
    }

    public List<CategoryDTO> findAll() {
        List<CategoryDTO> allDTO = new ArrayList<>();
        categoryRepository.findAll().forEach(category -> {
            CategoryDTO dto = new ModelMapper().map(category, CategoryDTO.class);
            allDTO.add(dto);
        });
        return allDTO;
    }

    public Category findById(long category_id) {
        Category category = categoryRepository.findByCategory_id(category_id);
        if (category != null) {
            return category;
        } else throw new RuntimeException("Category not found");
    }

    public Category update(CategoryDTO categoryDTO) {
        Category category = new ModelMapper().map(categoryDTO, Category.class);
        category.setCategory_id(categoryDTO.getCategory_id());
        return categoryRepository.save(category);
    }

    public void deleteById(long category_id) {
        findById(category_id);
        categoryRepository.deleteById(category_id);
    }

}
