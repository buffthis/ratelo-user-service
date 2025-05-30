package com.ratelo.blog.api.dto;

import com.ratelo.blog.domain.skill.Skill;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillResponse {
    private Long id;
    private String name;
    private ImageResponse logo;
    private byte level;
    private String description;

    public static SkillResponse from(Skill skill) {
        if (skill == null) return null;
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .logo(ImageResponse.from(skill.getLogo()))
                .level(skill.getLevel())
                .description(skill.getDescription())
                .build();
    }

    public static List<SkillResponse> from(List<Skill> skills) {
        if (skills == null) return List.of();
        return skills.stream()
                .map(SkillResponse::from)
                .toList();
    }
} 