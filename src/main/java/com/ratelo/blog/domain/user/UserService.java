package com.ratelo.blog.domain.user;

import com.ratelo.blog.api.dto.UserCreateRequest;
import com.ratelo.blog.api.dto.UserUpdateRequest;
import com.ratelo.blog.domain.career.Career;
import com.ratelo.blog.domain.career.CareerRepository;
import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.image.ImageRepository;
import com.ratelo.blog.domain.skill.Skill;
import com.ratelo.blog.domain.skill.SkillRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CareerRepository careerRepository;
    private final ImageRepository imageRepository;
    private final SkillRepository skillRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByCompanyId(Long companyId) {
        return careerRepository.findUsersByCompanyId(companyId);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElse(null);
    }

    public User createUser(UserCreateRequest request) {
        User user = request.toEntity();
        Image profileImage = null;
        if (request.getProfileImageId() != null) {
            profileImage = imageRepository.findById(request.getProfileImageId())
                .orElseThrow(() -> new EntityNotFoundException("Profile image not found with id: " + request.getProfileImageId()));
            user.setProfileImage(profileImage);
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        Image profileImage = null;
        if (request.getProfileImageId() != null) {
            profileImage = imageRepository.findById(request.getProfileImageId())
                .orElseThrow(() -> new EntityNotFoundException("Profile image not found with id: " + request.getProfileImageId()));
        }
        List<Career> careers = careerRepository.findByIdIn(request.getCareerIds());
        List<Skill> skills = skillRepository.findAllById(request.getSkillIds());
        user.update(request, profileImage, careers, skills);
        return userRepository.save(user);
    }
}
