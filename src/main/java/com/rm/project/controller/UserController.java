package com.rm.project.controller;

import com.rm.project.model.User;
import com.rm.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "User APIs", description = "APIs for managing users")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Get a user by ID",
            description = "Retrieve a specific user by their ID")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElse(null);
    }

    @Operation(summary = "User login",
            description = "Authenticate a user with username and password")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Login successful",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class, example = "1"))),
                    @ApiResponse(responseCode = "200", description = "Login failed",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class, example = "null")))
            }
    )
    @PostMapping("/login")
    public String userLogin(@RequestParam String userName, @RequestParam String password) {
        User user = userService.getUserByUsernameAndPassword(userName, password);
        if (!Objects.isNull(user)) {
            return String.valueOf(user.getUserId());
        } else {
            return null;
        }
    }

    @Operation(summary = "Register a new user",
            description = "Register a new user with the provided details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Registration successful",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(implementation = String.class, example = "Registered successfully")))
            }
    )
    @PostMapping("/register")
    public String userRegister(@RequestParam String userName, @RequestParam String password, @RequestParam String email, @RequestParam String phoneNumber) {
        User user = new User( userName, password, email, phoneNumber);
        userService.saveUser(user);
        return "Registered successfully";
    }

    @Operation(summary = "Create a new user",
            description = "Create a new user with the provided user object")
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
