package com.shopstyle.customer.controller;

import com.shopstyle.customer.DTO.TokenDTO;
import com.shopstyle.customer.config.security.TokenService;
import com.shopstyle.customer.DTO.UserDTO;
import com.shopstyle.customer.model.UserLogin;
import com.shopstyle.customer.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(userService.create(userDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid UserLogin userLogin) {
        UsernamePasswordAuthenticationToken login = userLogin.convert();
        try {
            Authentication authentication = authenticationManager.authenticate(login);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDTO(token, "Bearer "));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable long id) {
        System.out.println("chegou feign");
        return ResponseEntity.ok().body(new ModelMapper().map(userService.findById(id), UserDTO.class));
    }

    @PutMapping("users/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        return ResponseEntity.ok().body(new ModelMapper().map(userService.update(userDTO), UserDTO.class));
    }


}

