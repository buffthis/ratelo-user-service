package com.ratelo.blog.api.controller;

import com.ratelo.blog.api.dto.UserCreateRequest;
import com.ratelo.blog.api.dto.UserResponse;
import com.ratelo.blog.api.dto.UserUpdateRequest;
import com.ratelo.blog.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return UserResponse.from(userService.getUserById(id));
    }

    @GetMapping("/by-username/{id}")
    public UserResponse getUserByUsername(@PathVariable String username) {
        return UserResponse.from(userService.getUserByUsername(username));
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return UserResponse.from(userService.getAllUsers());
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserCreateRequest request) {
        return UserResponse.from(userService.createUser(request));
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request) {
        return UserResponse.from(userService.updateUser(id, request));
    }
}
