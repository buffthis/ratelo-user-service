package com.ratelo.blog.api.dto;

import com.ratelo.blog.domain.user.User;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String name;
    private String bio;
    private ImageResponse profileImage;
    private List<CareerResponse> careers;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .bio(user.getBio())
                .profileImage(ImageResponse.from(user.getProfileImage()))
                .careers(Optional.ofNullable(user.getCareers())
                        .orElse(Set.of())
                        .stream()
                        .map(CareerResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }

    public static List<UserResponse> from(List<User> users) {
        if (users == null) return List.of();
        return users.stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }
}