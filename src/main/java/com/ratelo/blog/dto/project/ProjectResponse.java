package com.ratelo.blog.dto.project;

import com.ratelo.blog.domain.project.Project;
import com.ratelo.blog.dto.image.ImageResponse;
import com.ratelo.blog.dto.user.UserResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String subtitle;
    private String content;
    private ImageResponse thumbnail;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String periodNote;
    private List<UserResponse> participants;
    private String externalUrl;
    private Boolean hidden;

    public static ProjectResponse from(Project project) {
        if (project == null) return null;
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .subtitle(project.getSubtitle())
                .content(project.getContent())
                .thumbnail(ImageResponse.from(project.getThumbnail()))
                .createdAt(project.getCreatedAt())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .periodNote(project.getPeriodNote())
                .participants(project.getParticipants() == null ? List.of() : project.getParticipants().stream().map(UserResponse::from).collect(Collectors.toList()))
                .externalUrl(project.getExternalUrl())
                .hidden(project.isHidden())
                .build();
    }

    public static List<ProjectResponse> from(List<Project> projects) {
        if (projects == null) return List.of();
        return projects.stream()
                .map(ProjectResponse::from)
                .toList();
    }
} 