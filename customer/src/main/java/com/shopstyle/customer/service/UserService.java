package com.shopstyle.customer.service;

import com.shopstyle.customer.model.User;
import com.shopstyle.customer.model.DTO.UserDTO;
import com.shopstyle.customer.respositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User create(UserDTO userDTO) {
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(new ModelMapper().map(userDTO, User.class));
    }

    public User findById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }

    public User update(UserDTO userDTO) {
        findById(userDTO.getId());
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(new ModelMapper().map(userDTO, User.class));
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        } else return user;
    }
}
