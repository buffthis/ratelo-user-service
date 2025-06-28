package com.ratelo.blog.dto.user;

import com.ratelo.blog.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSimpleResponse {
    private Long id;
    private String username;
    private String name;
    private String bio;
    private String userType;
    private boolean disabled;
    private boolean masked;

    public static UserSimpleResponse from(User user) {
        return UserSimpleResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .bio(user.getBio())
                .userType(user.getUserType().name())
                .disabled(user.isDisabled())
                .masked(user.isMasked())
                .build();
    }
} 