package com.shopstyle.checkout.service;

import com.shopstyle.checkout.model.DTO.PaymentDTO;
import com.shopstyle.checkout.model.Payment;
import com.shopstyle.checkout.respositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private PaymentRepository paymentRepository;

    private PaymentDTO paymentDTO;
    private Payment payment;
    private List<Payment> payments = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPayment();
        payments.add(payment);
    }

    private void startPayment() {
        paymentDTO = new PaymentDTO(1l, "CreditTest", 10, true);
        payment = new Payment(1l, "CreditTest", 10, true);

    }

    @Test
    void create() {
        when(paymentRepository.save(any())).thenReturn(payment);
        Payment response = paymentService.create(paymentDTO);
        assertNotNull(response);
        assertEquals(Payment.class, response.getClass());
        assertEquals(payment.getPayment_id(), response.getPayment_id());
        assertEquals(payment.getType(), response.getType());
        assertEquals(payment.getDiscount(), response.getDiscount());
        assertEquals(payment.getStatus(), response.getStatus());
    }

    @Test
    void findById() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(payment));
        PaymentDTO response = paymentService.findById(1l);
        assertNotNull(response);
        assertEquals(PaymentDTO.class, response.getClass());
        assertEquals(paymentDTO.getPayment_id(), response.getPayment_id());
        assertEquals(paymentDTO.getType(), response.getType());
        assertEquals(paymentDTO.getDiscount(), response.getDiscount());
        assertEquals(paymentDTO.getStatus(), response.getStatus());
    }

    @Test
    void update() {
        when(paymentRepository.save(any())).thenReturn(payment);
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(payment));
        Payment response = paymentService.update(paymentDTO);
        assertNotNull(response);
        assertEquals(Payment.class, response.getClass());
        assertEquals(payment.getPayment_id(), response.getPayment_id());
        assertEquals(payment.getType(), response.getType());
        assertEquals(payment.getDiscount(), response.getDiscount());
        assertEquals(payment.getStatus(), response.getStatus());
    }

    @Test
    void deleteById() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(payment));
        doNothing().when(paymentRepository).deleteById(anyLong());
        paymentService.deleteById(1l);
        verify(paymentRepository, times(1)).deleteById(1l);

    }

    @Test
    void findAll() {
        when(paymentRepository.findAll()).thenReturn(payments);

        List<PaymentDTO> all = paymentService.findAll();

        assertEquals(all.size(), payments.size());
        assertEquals(all.get(0).getType(), payments.get(0).getType());

    }
}