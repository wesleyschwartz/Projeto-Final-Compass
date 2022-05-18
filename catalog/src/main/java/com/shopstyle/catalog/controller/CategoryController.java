package com.shopstyle.catalog.controller;

import com.shopstyle.catalog.model.Category;
import com.shopstyle.catalog.model.DTO.CategoryDTO;
import com.shopstyle.catalog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @PostMapping
    ResponseEntity<Category> create(@RequestBody @Valid  CategoryDTO categoryDTO) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{category_id}").buildAndExpand(service.create(categoryDTO).getCategory_id()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{category_id}")
    ResponseEntity<Category> update(@PathVariable long category_id, @RequestBody @Valid CategoryDTO categoryDTO) {
        service.findById(category_id);
        categoryDTO.setCategory_id(category_id);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{category_id}").buildAndExpand(service.update(categoryDTO).getCategory_id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{category_id}")
    ResponseEntity<Object> delete(@PathVariable long category_id) {
        service.deleteById(category_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAllWithoutProducts() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{category_id}/products")
    public ResponseEntity<Category> findByIdWithProducts(@PathVariable long category_id) {
        return ResponseEntity.ok().body(service.findById(category_id));
    }

}
