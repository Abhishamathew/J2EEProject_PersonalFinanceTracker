package com.rm.project.service;

import com.rm.project.model.User;
import com.rm.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
