package com.shopstyle.customer.controller;

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

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void createWithSuccess() throws Exception {
        RequestBuilder requestBuilder = post("/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Wesley\"," +
                        "\"lastName\": \"Schwartz\"," +
                        "\"sex\": \"Masculino\"," +
                        "\"cpf\": \"000.000.555-05\"," +
                        "\"birthdate\": \"22/04/1995\"," +
                        "\"email\": \"wesley1@email.com.br\"," +
                        "\"password\": \"12345678\"," +
                        "\"active\": true }")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }
    @Test
    void loginWithInsucces() throws Exception {
        URI uri = (new URI("/v1/login"));
        String json = "{\"email\": \"invalid@email.com\", \"password\": \"12345678\"}";
        mockMvc.perform(post(uri)
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void loginSucces() throws Exception {
        URI uri = (new URI("/v1/login"));
        String json = "{\"email\": \"wesley@email.com.br\", \"password\": \"12345678\"}";
        mockMvc.perform(post(uri)
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void getByIdThenReturnSuccess() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/v1/users/1");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    void updateWithSuccess() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/v1/users/1")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Wesley2\"," +
                        "\"lastName\": \"Schwartz\"," +
                        "\"sex\": \"Masculino\"," +
                        "\"cpf\": \"000.000.555-05\"," +
                        "\"birthdate\": \"22/04/1995\"," +
                        "\"email\": \"wesley@email.com.br\"," +
                        "\"password\": \"12345678\"," +
                        "\"active\": true }")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        System.out.println(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }




}