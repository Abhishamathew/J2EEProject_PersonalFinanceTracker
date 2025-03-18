package com.rm.project.controller;

import com.rm.project.model.User;
import com.rm.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElse(null);
    }

    @PostMapping("/login")
    public String userLogin(@RequestParam String userName, @RequestParam String password) {
        User user = userService.getUserByUsernameAndPassword(userName, password);
        if (!Objects.isNull(user)) {
            return String.valueOf(user.getUserId());
        } else {
            return null;
        }
    }

    @PostMapping("/register")
    public String userRegister(@RequestParam String userName, @RequestParam String password, @RequestParam String email, @RequestParam String phoneNumber) {
        User user = new User( userName, password, email, phoneNumber);
        userService.saveUser(user);
        return "Registered successfully";
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
