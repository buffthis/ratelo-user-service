package com.ratelo.blog.dto.skill;

import com.ratelo.blog.domain.skill.Skill;
import com.ratelo.blog.dto.tool.ToolResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillResponse {
    private Long id;
    private ToolResponse tool;
    private byte level;
    private String description;
    private Boolean hidden;

    public static SkillResponse from(Skill skill) {
        if (skill == null) return null;
        return SkillResponse.builder()
                .id(skill.getId())
                .tool(ToolResponse.from(skill.getTool()))
                .level(skill.getLevel())
                .description(skill.getDescription())
                .hidden(skill.isHidden())
                .build();
    }

    public static List<SkillResponse> from(List<Skill> skills) {
        if (skills == null) return List.of();
        return skills.stream()
                .map(SkillResponse::from)
                .toList();
    }
} 