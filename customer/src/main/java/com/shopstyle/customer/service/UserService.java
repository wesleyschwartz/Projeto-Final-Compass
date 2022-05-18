package com.shopstyle.customer.service;

import com.shopstyle.customer.model.User;
import com.shopstyle.customer.DTO.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User create(UserDTO userDTO);

    User findById(long id);

    Object update(UserDTO userDTO);

    User findByEmail(String email);
}
