package com.ratelo.blog.dto.skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillUpdateRequest {
    private Long toolId;
    private byte level;
    private String description;
} 