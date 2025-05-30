package com.ratelo.blog.domain.skill;

import com.ratelo.blog.api.dto.SkillCreateRequest;
import com.ratelo.blog.api.dto.SkillUpdateRequest;
import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.image.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    private final ImageRepository imageRepository;

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id).orElse(null);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill createSkill(SkillCreateRequest request) {
        Skill skill = request.toEntity();
        if (request.getLogoId() != null) {
            Image logo = imageRepository.findById(request.getLogoId())
                    .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
            skill.setLogo(logo);
        }
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, SkillUpdateRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found with id: " + id));
        Image logo = null;
        if (request.getLogoId() != null) {
            logo = imageRepository.findById(request.getLogoId())
                    .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
        }
        skill.update(request, logo);
        return skillRepository.save(skill);
    }
} 