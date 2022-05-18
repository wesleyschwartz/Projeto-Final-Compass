package com.shopstyle.catalog.controller;

import com.shopstyle.catalog.model.DTO.VariantDTO;
import com.shopstyle.catalog.model.Variant;
import com.shopstyle.catalog.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1/variations")
public class VariantController {

    @Autowired
    private VariantService variantService;

    @PostMapping
    ResponseEntity<Variant> createVariant(@RequestBody @Valid VariantDTO variantDTO) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(variantService.create(variantDTO)
                        .getVariant_id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{variant_id}")
    ResponseEntity<Variant> update(@PathVariable long variant_id, @RequestBody @Valid VariantDTO variantDTO) {
        variantDTO.setVariant_id(variant_id);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{variant_id}").buildAndExpand(variantService.update(variantDTO).getVariant_id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{variant_id}")
    ResponseEntity<Object> delete(@PathVariable long variant_id) {
        variantService.deleteById(variant_id);
        return ResponseEntity.ok().build();
    }


   @GetMapping("/{variant_id}")
    public ResponseEntity<Variant> findVariantById(@PathVariable long variant_id) {
        return ResponseEntity.ok().body(variantService.findById(variant_id));
    }
}
