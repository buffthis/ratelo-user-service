package com.ratelo.blog.api.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillsUpdateRequest {
    private List<Long> skillIds;
} 