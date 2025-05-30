package com.ratelo.blog.api.dto;

import com.ratelo.blog.domain.image.Image;
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
    private Long profileImageId;

    public User toEntity(Image profileImage) {
        return User.builder()
                .username(username)
                .name(name)
                .profileImage(profileImage)
                .build();
    }
}