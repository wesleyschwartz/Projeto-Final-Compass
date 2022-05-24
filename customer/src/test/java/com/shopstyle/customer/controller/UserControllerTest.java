package com.shopstyle.customer.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.net.URI;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
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
        RequestBuilder requestBuilder = get("/v1/users/1");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getByIdThenReturnNoSuchElementException() throws Exception {
        mockMvc.perform(get("/v1/users/9999"))
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException))
                .andExpect(result ->
                        assertEquals("{\"status\":400,\"error\":\"No value present\"}",
                                result.getResponse().getContentAsString()));
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

    @Test
    void createWithLessThan3CharacteresFirstName() throws Exception {
        String json = "{\"firstName\":\"12\"," +
                "\"lastName\": \"Schwartz\"," +
                "\"sex\": \"Masculino\"," +
                "\"cpf\": \"000.000.555-05\"," +
                "\"birthdate\": \"22/04/1995\"," +
                "\"email\": \"wesley1@email.com.br\"," +
                "\"password\": \"12345678\"," +
                "\"active\": true }";

        mockMvc.perform(post("/v1/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))

                .andExpect(handler().handlerType(UserController.class))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result ->
                        assertEquals("{\"status\":400,\"error\":\"firstName firstName must contain at least 3 characters\"}",
                                result.getResponse().getContentAsString()));


    }

    @Test
    void createWithWrongSex() throws Exception {
        String json = "{\"firstName\":\"Wesley\"," +
                "\"lastName\": \"Schwartz\"," +
                "\"sex\": \"\"," +
                "\"cpf\": \"000.000.555-05\"," +
                "\"birthdate\": \"22/04/1995\"," +
                "\"email\": \"wesley1@email.com.br\"," +
                "\"password\": \"12345678\"," +
                "\"active\": true }";

        mockMvc.perform(post("/v1/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andExpect(result ->
                        assertEquals("{\"status\":400,\"error\":\"camp: sex invalid. Valid values: Masculino || Feminino\"}",
                                result.getResponse().getContentAsString()));
    }

    @Test
    void createWithWrongCpf() throws Exception {
        String json = "{\"firstName\":\"Wesley\"," +
                "\"lastName\": \"Schwartz\"," +
                "\"sex\": \"Masculino\"," +
                "\"cpf\": \".000.555-05\"," +
                "\"birthdate\": \"22/04/1995\"," +
                "\"email\": \"wesley1@email.com.br\"," +
                "\"password\": \"12345678\"," +
                "\"active\": true }";

        mockMvc.perform(post("/v1/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result ->
                        assertEquals("{\"status\":400,\"error\":\"cpf camp invalid, this camp should be xxx.xxx.xxx-xx\"}",
                                result.getResponse().getContentAsString()));
    }

    @Test
    void createWithWrongBirthDate() throws Exception {
        String json = "{\"firstName\":\"Wesley\"," +
                "\"lastName\": \"Schwartz\"," +
                "\"sex\": \"Masculino\"," +
                "\"cpf\": \"000.000.555-05\"," +
                "\"birthdate\": \"/04/1995\"," +
                "\"email\": \"wesley1@email.com.br\"," +
                "\"password\": \"12345678\"," +
                "\"active\": true }";

        mockMvc.perform(post("/v1/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andExpect(result ->
                        assertEquals("{\"status\":400,\"error\":\"camp: birthdate invalid. Format valid: dd/MM/yyyy\"}",
                                result.getResponse().getContentAsString()));
    }

    @Test
    void createWithEmailThatAlreadyExists() throws Exception {
        String json = "{\"firstName\":\"Wesley\"," +
                "\"lastName\": \"Schwartz\"," +
                "\"sex\": \"Masculino\"," +
                "\"cpf\": \"000.000.555-05\"," +
                "\"birthdate\": \"22/04/1995\"," +
                "\"email\": \"wesley@email.com.br\"," +
                "\"password\": \"12345678\"," +
                "\"active\": true }";

        mockMvc.perform(post("/v1/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegrityViolationException))
                .andExpect(result ->
                        assertEquals("{\"status\":400,\"error\":\"Key (email)=(wesley@email.com.br) already exists.\"}",
                                result.getResponse().getContentAsString()));
    }


}