package com.auth.authgateway.service;

import com.auth.authgateway.entities.User;
import com.auth.authgateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
