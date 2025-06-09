package com.ratelo.blog.api;

import com.ratelo.blog.domain.user.UserService;
import com.ratelo.blog.domain.user.UserType;
import com.ratelo.blog.dto.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
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

    @PatchMapping("/{id}")
    public UserResponse patchUser(@PathVariable Long id, @RequestBody UserPatchRequest request) {
        return UserResponse.from(userService.patchUser(id, request));
    }

    @GetMapping("/cursor")
    public Slice<UserResponse> getUsersByCursor(
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UserType userType
    ) {
        return userService.getUsersByCursor(lastId, pageSize, name, userType);
    }

    @PostMapping("/bulk")
    public List<UserResponse> createUsers(@RequestBody UserBulkCreateRequest request) {
        return UserResponse.from(userService.createUsers(request.getUsers()));
    }

    @GetMapping(value = "/{id}/svg-card", produces = "image/svg+xml")
    public String getUserSvgCard(@PathVariable Long id) {
        return userService.getUserSvgCard(id);
    }
}
