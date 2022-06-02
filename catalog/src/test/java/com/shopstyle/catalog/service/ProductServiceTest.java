package com.shopstyle.catalog.service;

import com.shopstyle.catalog.model.Category;
import com.shopstyle.catalog.model.DTO.ProductDTO;
import com.shopstyle.catalog.model.Product;
import com.shopstyle.catalog.repository.CategoryRepository;
import com.shopstyle.catalog.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private SequenceGeneration sequenceGeneration;
    @Mock
    MongoTemplate mongoTemplate;
    private List<Category> categoryList;
    private Category category1;
    private Category category2;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCategoryAndProduct();
    }

    @Test
    void createCategoryActive() {

        when(sequenceGeneration.getSequenceNumber(any())).thenReturn(1l);
        when(productRepository.save(any())).thenReturn(product);
        when(categoryService.create(any())).thenReturn(category1);
        when(categoryRepository.findByCategory_id(category1.getCategory_id())).thenReturn(category1);
        mongoTemplate.save(product);
        Product response = productService.create(new ModelMapper().map(this.product, ProductDTO.class));

        assertNotNull(response);
        assertEquals(Product.class, response.getClass());
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findAll() {
    }


    private void startCategoryAndProduct() {
        categoryList = new ArrayList<>();
        category1 = new Category();
        category1.setName("Category1");
        category1.setCategory_id(1l);
        category1.setActive(true);
        category1.setProductList(new ArrayList<>());
        categoryList.add(category1);
        category2 = new Category();
        category2 = new Category();
        category2.setCategory_id(2l);
        category2.setName("Category2");
        category2.setActive(false);
        category2.setProductList(new ArrayList<>());
        categoryList.add(category2);
        product = new Product();
        product.setProduct_id(1l);
        product.setName("Product1");
        product.setDescription("Product para Test1");
        product.setActive(true);
        ArrayList<Long> longs = new ArrayList<>();
        longs.add(1l);
        product.setCategory_ids(longs);

    }
}