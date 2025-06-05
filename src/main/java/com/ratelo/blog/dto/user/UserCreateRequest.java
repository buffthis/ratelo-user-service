package com.ratelo.blog.dto.user;

import com.ratelo.blog.domain.user.User;
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
    private Long profileImageId;
    private UserType userType = UserType.MEMBER;

    public User toEntity() {
        return User.builder()
                .username(username)
                .name(name)
                .bio(bio)
                .userType(userType)
                .build();
    }
}