package com.shopstyle.checkout.feign;

import com.shopstyle.checkout.feign.clients.userFeignEntities.TokenDTO;
import com.shopstyle.checkout.feign.clients.userFeignEntities.User;
import com.shopstyle.checkout.feign.clients.userFeignEntities.UserLogin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Component
@FeignClient(name = "customer", url = "http://localhost:8765", path = "/v1")
public interface UserFeignClient {
    @PostMapping("/users")
    public ResponseEntity<User> create(@RequestBody User userDTO);
    @GetMapping("/users/{id}")
    ResponseEntity<User> getById(@RequestHeader("Authorization") String token, @PathVariable long id);
    @GetMapping("/login")
    ResponseEntity<TokenDTO> login(@RequestBody @Valid UserLogin userlogin);



}
