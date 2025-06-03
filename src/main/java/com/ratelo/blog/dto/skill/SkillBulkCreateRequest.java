package com.ratelo.blog.dto.skill;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SkillBulkCreateRequest {
    private List<SkillCreateRequest> skills;
} 