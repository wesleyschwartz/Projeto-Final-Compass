package com.shopstyle.history.controller;

import com.shopstyle.history.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class HistoryController {
    @Autowired
    private UserService userService;
    @GetMapping("/historic/user/{id_user}")
    public ResponseEntity<?> getHistoryUser(@PathVariable long id_user){
        return ResponseEntity.ok().body(userService.findById(id_user));
    }
}
