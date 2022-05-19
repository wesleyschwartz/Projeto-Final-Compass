package com.shopstyle.customer.controller;

import com.shopstyle.customer.config.security.TokenService;
import com.shopstyle.customer.model.DTO.UserDTO;
import com.shopstyle.customer.model.Sex;
import com.shopstyle.customer.model.User;
import com.shopstyle.customer.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class UserControllerTest {
    public static final LocalDate BIRTHDATE = LocalDate.of(1995, 4, 22);
    public static final long ID = 1l;
    public static final String FIRST_NAME = "Wesley";
    public static final String LAST_NAME = "Schwartz";
    public static final Sex SEX = Sex.Masculino;
    public static final String CPF = "816.372.334-34";
    public static final String EMAIL = "wesley@email.com";
    public static final String PASSWORD = "12345678";
    public static final boolean ACTIVE = true;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenService tokenService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void create() {
    }

    @Test
    void login() {
    }

    @Test
    void getByIdThenReturnSuccess() {
        when(userService.findById(anyLong())).thenReturn(user);
        when(modelMapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response =
                userController.getById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(FIRST_NAME, response.getBody().getFirstName());
        assertEquals(LAST_NAME, response.getBody().getLastName());
        assertEquals(SEX, response.getBody().getSex());
        assertEquals(CPF, response.getBody().getCpf());
        assertEquals(BIRTHDATE, response.getBody().getBirthdate());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
        assertEquals(ACTIVE, response.getBody().getActive());

    }

    @Test
    void update() {
    }

    private void startUser() {
        user = new User(ID, FIRST_NAME, LAST_NAME, SEX, CPF,
                BIRTHDATE, EMAIL, PASSWORD, ACTIVE);

        userDTO = new UserDTO(ID, FIRST_NAME, LAST_NAME, SEX, CPF,
                BIRTHDATE, EMAIL, PASSWORD, ACTIVE);

        optionalUser = optionalUser.of(new User(ID, FIRST_NAME, LAST_NAME, SEX, CPF,
                BIRTHDATE, EMAIL, PASSWORD, ACTIVE));
    }


}