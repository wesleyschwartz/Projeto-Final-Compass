package com.shopstyle.checkout.controller;

import com.shopstyle.checkout.model.Purchase;
import com.shopstyle.checkout.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService service;

    @PostMapping
    ResponseEntity<Purchase> create(@RequestBody @Valid Purchase purchase) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(service.create(purchase).getPurchase_id()).toUri();

        return ResponseEntity.created(uri).build();
    }
}

