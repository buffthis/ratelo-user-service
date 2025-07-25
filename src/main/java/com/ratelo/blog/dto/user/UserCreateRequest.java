package com.ratelo.blog.dto.user;

import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.domain.user.UserType;
import com.ratelo.blog.dto.image.ImageCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    @NotNull
    private String username;
    @NotNull
    private String name;
    private String bio;
    private ImageCreateRequest imageCreateRequest;
    private UserType userType = UserType.TEST;
    @NotNull
    private String password;

    public User toEntity() {
        return User.builder()
                .username(username)
                .name(name)
                .bio(bio)
                .userType(userType)
                .password(password)
                .build();
    }
}