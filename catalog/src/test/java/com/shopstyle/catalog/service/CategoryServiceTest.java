package com.shopstyle.catalog.service;

import com.shopstyle.catalog.model.Category;
import com.shopstyle.catalog.model.DTO.CategoryDTO;
import com.shopstyle.catalog.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private SequenceGeneration sequenceGeneration;
    private CategoryDTO categoryDTO;
    private Category category;
    private List<Category> categoryList = new ArrayList<>();
    private List<CategoryDTO> categoryListDTO = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCategory();
        categoryList.add(category);
    }

    private void startCategory() {
        categoryDTO = new CategoryDTO();
        categoryDTO.setName("CategoryTest");
        categoryDTO.setActive(true);

        category = new Category();
        category.setName("CategoryTest");
        category.setActive(true);

    }

    @Test
    void create() {
        when(sequenceGeneration.getSequenceNumber(any())).thenReturn(1l);
        when(categoryRepository.save(any())).thenReturn(category);

        Category response = categoryService.create(categoryDTO);

        assertNotNull(response);
        assertEquals(Category.class, response.getClass());
        assertEquals("CategoryTest", response.getName());
        assertEquals(true, response.getActive());
        assertEquals(response.getProductList().size(), 0);

    }

    @Test
    void findAll() {
        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<CategoryDTO> all = categoryService.findAll();

        assertEquals(all.size(), categoryList.size());
        assertEquals(all.get(0).getName(), categoryList.get(0).getName());
    }

    @Test
    void findById() {
        when(categoryRepository.findByCategory_id(anyLong())).thenReturn(category);
        category.setCategory_id(1l);
        Category response = categoryService.findById(category.getCategory_id());

        assertEquals(Category.class, response.getClass());
        assertEquals("CategoryTest", response.getName());
        assertEquals(true, response.getActive());
    }

    @Test
    void findByIdThatNoExist() {
        category.setCategory_id(1l);
        try {
            Category response = categoryService.findById(category.getCategory_id());

        } catch (Exception e) {
            assertEquals("Category not found", e.getMessage());
            assertEquals(RuntimeException.class, e.getClass());
        }
    }

    @Test
    void update() {
        when(sequenceGeneration.getSequenceNumber(any())).thenReturn(1l);
        when(categoryRepository.save(any())).thenReturn(category);

        Category response = categoryService.update(categoryDTO);

        assertNotNull(response);
        assertEquals(Category.class, response.getClass());
        assertEquals("CategoryTest", response.getName());
        assertEquals(true, response.getActive());
        assertEquals(response.getProductList().size(), 0);
    }

    @Test
    void deleteById() {
        when(categoryRepository.findByCategory_id(anyLong())).thenReturn(category);
        doNothing().when(categoryRepository).deleteById(anyLong());
        categoryService.deleteById(1l);
        verify(categoryRepository, times(1)).deleteById(1l);

    }
}