package com.ratelo.blog.dto.user;

import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.dto.career.CareerResponse;
import com.ratelo.blog.dto.image.ImageResponse;
import com.ratelo.blog.dto.post.PostSummaryResponse;
import com.ratelo.blog.dto.project.ProjectSummaryResponse;
import com.ratelo.blog.dto.skill.SkillResponse;
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
    private List<SkillResponse> skills;
    private List<ProjectSummaryResponse> projectSummaries;
    private List<PostSummaryResponse> postSummaries;

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
                        .filter(career -> !career.isHidden())
                        .sorted((a, b) -> b.getStartDate().compareTo(a.getStartDate()))
                        .map(CareerResponse::from)
                        .collect(Collectors.toList()))
                .skills(Optional.ofNullable(user.getSkills())
                        .orElse(Set.of())
                        .stream()
                        .map(SkillResponse::from)
                        .collect(Collectors.toList()))
                .projectSummaries(Optional.ofNullable(user.getProjects())
                        .orElse(Set.of())
                        .stream()
                        .map(ProjectSummaryResponse::from)
                        .collect(Collectors.toList()))
                .postSummaries(Optional.ofNullable(user.getPosts())
                        .orElse(Set.of())
                        .stream()
                        .map(PostSummaryResponse::from)
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