package com.ratelo.blog.dto.skill;

import com.ratelo.blog.domain.skill.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillCreateRequest {
    private Long toolId;
    private byte level;
    private String description;

    @NotNull
    private Long userId;

    public Skill toEntity() {
        return Skill.builder()
                .level(level)
                .description(description)
                .build();
    }
} 