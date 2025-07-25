package com.ratelo.blog.api;

import com.ratelo.blog.domain.skill.SkillService;
import com.ratelo.blog.dto.skill.SkillCreateRequest;
import com.ratelo.blog.dto.skill.SkillResponse;
import com.ratelo.blog.dto.skill.SkillUpdateRequest;
import com.ratelo.blog.dto.skill.SkillBulkCreateRequest;
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

    @PostMapping("/bulk")
    public List<SkillResponse> createSkills(@RequestBody SkillBulkCreateRequest request) {
        return SkillResponse.from(skillService.createSkills(request.getSkills()));
    }

    @PutMapping("/{id}")
    public SkillResponse updateSkill(
            @PathVariable Long id,
            @RequestBody SkillUpdateRequest request) {
        return SkillResponse.from(skillService.updateSkill(id, request));
    }
} 