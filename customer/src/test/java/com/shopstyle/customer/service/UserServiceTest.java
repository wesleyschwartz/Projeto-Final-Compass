package com.shopstyle.customer.service;

import com.shopstyle.customer.model.DTO.UserDTO;
import com.shopstyle.customer.model.Sex;
import com.shopstyle.customer.model.User;
import com.shopstyle.customer.respositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    public static final LocalDate BIRTHDATE = LocalDate.of(1995, 4, 22);
    public static final long ID = 1l;
    public static final String FIRST_NAME = "Wesley";
    public static final String LAST_NAME = "Schwartz";
    public static final Sex SEX = Sex.Masculino;
    public static final String CPF = "816.372.334-34";
    public static final String EMAIL = "wesley@email.com";
    public static final String PASSWORD = "12345678";
    public static final boolean ACTIVE = true;
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private User user;

    private UserDTO userDTO;

    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();

    }


    @Test
    void create() {

        when(userRepository.save(any())).thenReturn(user);

        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        User response = userService.create(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(FIRST_NAME, response.getFirstName());
        assertEquals(LAST_NAME, response.getLastName());
        assertEquals(SEX, response.getSex());
        assertEquals(CPF, response.getCpf());
        assertEquals(BIRTHDATE, response.getBirthdate());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(ACTIVE, response.getActive());
    }

    @Test
    void findById() {
        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
        User response = userService.findById(ID);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(FIRST_NAME, response.getFirstName());
        assertEquals(LAST_NAME, response.getLastName());
        assertEquals(SEX, response.getSex());
        assertEquals(CPF, response.getCpf());
        assertEquals(BIRTHDATE, response.getBirthdate());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(ACTIVE, response.getActive());
    }

    @Test
    void update() {
        findById();
        when(userRepository.save(any())).thenReturn(user);

        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        User response = userService.update(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(FIRST_NAME, response.getFirstName());
        assertEquals(LAST_NAME, response.getLastName());
        assertEquals(SEX, response.getSex());
        assertEquals(CPF, response.getCpf());
        assertEquals(BIRTHDATE, response.getBirthdate());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(ACTIVE, response.getActive());
    }

    @Test
    void loadUserByUsernameWhenUserNotNull() {
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        User response = (User) userService.loadUserByUsername(EMAIL);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(FIRST_NAME, response.getFirstName());
        assertEquals(LAST_NAME, response.getLastName());
        assertEquals(SEX, response.getSex());
        assertEquals(CPF, response.getCpf());
        assertEquals(BIRTHDATE, response.getBirthdate());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(ACTIVE, response.getActive());
    }

    @Test
    void loadUserByUsernameWhenUserNull() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        try {
            userService.loadUserByUsername(EMAIL);
        }catch (Exception e){
            assertEquals(UsernameNotFoundException.class, e.getClass());
            assertEquals(EMAIL + " not found", e.getMessage());
        }
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