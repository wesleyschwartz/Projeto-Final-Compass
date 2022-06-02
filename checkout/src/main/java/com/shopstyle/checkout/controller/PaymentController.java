package com.shopstyle.checkout.controller;

import com.shopstyle.checkout.model.Payment;
import com.shopstyle.checkout.model.DTO.PaymentDTO;
import com.shopstyle.checkout.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping
    ResponseEntity<Payment> create(@RequestBody @Valid PaymentDTO paymentDTO) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(service.create(paymentDTO).getPayment_id()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Payment> update(@PathVariable long id, @RequestBody @Valid PaymentDTO paymentDTO) {
        paymentDTO.setPayment_id(id);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(service.update(paymentDTO).getPayment_id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> findById(@PathVariable long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

}

