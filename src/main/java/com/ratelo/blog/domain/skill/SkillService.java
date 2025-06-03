package com.ratelo.blog.domain.skill;

import com.ratelo.blog.domain.tool.Tool;
import com.ratelo.blog.domain.tool.ToolRepository;
import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.domain.user.UserRepository;
import com.ratelo.blog.dto.skill.SkillCreateRequest;
import com.ratelo.blog.dto.skill.SkillUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id).orElse(null);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill createSkill(SkillCreateRequest request) {
        Skill skill = request.toEntity();
        Tool tool = toolRepository.findById(request.getToolId())
                .orElseThrow(() -> new EntityNotFoundException("Tool not found with id: " + request.getToolId()));
        skill.setTool(tool);
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));
        skill.setUser(user);
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, SkillUpdateRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found with id: " + id));
        Tool tool = toolRepository.findById(request.getToolId())
                .orElseThrow(() -> new EntityNotFoundException("Tool not found with id: " + request.getToolId()));
        skill.update(request, tool);
        return skillRepository.save(skill);
    }

    public List<Skill> createSkills(List<SkillCreateRequest> requests) {
        List<Skill> skills = requests.stream().map(request -> {
            Skill skill = request.toEntity();
            Tool tool = toolRepository.findById(request.getToolId())
                .orElseThrow(() -> new EntityNotFoundException("Tool not found with id: " + request.getToolId()));
            skill.setTool(tool);
            User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));
            skill.setUser(user);
            return skill;
        }).toList();
        return skillRepository.saveAll(skills);
    }
} 