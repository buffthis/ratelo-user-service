package com.ratelo.blog.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillUpdateRequest {
    private String name;
    private Long logoId;
    private byte level;
    private String description;
} 