package com.ratelo.blog.api.dto;

import com.ratelo.blog.domain.skill.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillCreateRequest {
    private String name;
    private Long logoId;
    private byte level;
    private String description;

    public Skill toEntity() {
        return Skill.builder()
                .name(name)
                .level(level)
                .description(description)
                .build();
    }
} 