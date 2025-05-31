package com.ratelo.blog.api;

import com.ratelo.blog.domain.user.UserService;
import com.ratelo.blog.dto.user.*;
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

    @GetMapping("/by-username/{username}")
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

    @PatchMapping("/{id}/profile-image")
    public UserResponse updateProfileImage(
            @PathVariable Long id,
            @RequestBody UserProfileImageUpdateRequest request) {
        return UserResponse.from(userService.updateProfileImage(id, request));
    }

    @PatchMapping("/{id}/careers")
    public UserResponse updateCareers(
            @PathVariable Long id,
            @RequestBody UserCareersUpdateRequest request) {
        return UserResponse.from(userService.updateCareers(id, request));
    }

    @PatchMapping("/{id}/skills")
    public UserResponse updateSkills(
            @PathVariable Long id,
            @RequestBody UserSkillsUpdateRequest request) {
        return UserResponse.from(userService.updateSkills(id, request));
    }
}
