package com.ratelo.blog.api.controller;

import com.ratelo.blog.api.dto.SkillCreateRequest;
import com.ratelo.blog.api.dto.SkillResponse;
import com.ratelo.blog.api.dto.SkillUpdateRequest;
import com.ratelo.blog.domain.skill.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping("/{id}")
    public SkillResponse getSkillById(@PathVariable Long id) {
        return SkillResponse.from(skillService.getSkillById(id));
    }

    @GetMapping
    public List<SkillResponse> getAllSkills() {
        return SkillResponse.from(skillService.getAllSkills());
    }

    @PostMapping
    public SkillResponse createSkill(@RequestBody SkillCreateRequest request) {
        return SkillResponse.from(skillService.createSkill(request));
    }

    @PutMapping("/{id}")
    public SkillResponse updateSkill(
            @PathVariable Long id,
            @RequestBody SkillUpdateRequest request) {
        return SkillResponse.from(skillService.updateSkill(id, request));
    }
} 