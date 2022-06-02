package com.shopstyle.checkout.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWithSuccess() throws Exception {
        String json = "{\"type\":\"CreditTest\"," +
                "\"discount\": \"10\"," +
                "\"status\": \"true\"}";
        RequestBuilder requestBuilder = post("/v1/payments")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void createWithNoSuccess() throws Exception {
        String json = "{" +
                "\"discount\": \"10\"," +
                "\"status\": \"true\"}";
        mockMvc.perform(post("/v1/payments")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))

                .andExpect(handler().handlerType(PaymentController.class))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result ->
                        assertEquals("{\"status\":400,\"error\":\"type must not be null\"}",
                                result.getResponse().getContentAsString()));
    }

    @Test
    void updateSuccess() throws Exception {
        String json = "{\"type\":\"CreditTest2\"," +
                "\"discount\": \"10\"," +
                "\"status\": \"true\"}";
        RequestBuilder requestBuilder = put("/v1/payments/10")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void findAll() throws Exception {
        RequestBuilder requestBuilder = get("/v1/payments");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void delete() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/payments/11");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    void findById() throws Exception {
        RequestBuilder requestBuilder = get("/v1/payments/10");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void findByIdWhenNoSuchElementException() throws Exception {
        mockMvc.perform(get("/v1/payments/4564")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(handler().handlerType(PaymentController.class))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException))
                .andExpect(result ->
                        assertEquals("{\"status\":400,\"error\":\"No value present\"}",
                                result.getResponse().getContentAsString()));
    }
}