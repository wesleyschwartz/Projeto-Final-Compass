package com.shopstyle.history.service;

import com.shopstyle.history.entities.DTO.UserDTO;
import com.shopstyle.history.entities.User;
import com.shopstyle.history.repositories.UserRepository;
import com.shopstyle.history.entities.toshow.UserShow;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public boolean existById(Long user_id) {
        return userRepository.existsById(user_id);
    }

    public void save(UserDTO userDTO) {
        userRepository.save(new ModelMapper().map(userDTO, User.class));
    }

    public UserShow findById(Long user_id) {
        User user = userRepository.findById(user_id).get();
        if (user != null) {
            UserShow userShow = new ModelMapper().map(user, UserShow.class);
            return userShow;
        } else throw new RuntimeException("User not found");
    }
}
