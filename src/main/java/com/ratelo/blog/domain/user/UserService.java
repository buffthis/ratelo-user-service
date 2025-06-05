package com.ratelo.blog.domain.user;

import com.ratelo.blog.domain.career.Career;
import com.ratelo.blog.domain.career.CareerRepository;
import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.image.ImageRepository;
import com.ratelo.blog.domain.skill.Skill;
import com.ratelo.blog.domain.skill.SkillRepository;
import com.ratelo.blog.dto.user.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CareerRepository careerRepository;
    private final ImageRepository imageRepository;
    private final SkillRepository skillRepository;
    private final UserPatchMapper userPatchMapper;

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
        if (request.getImageCreateRequest() != null) {
            user.setProfileImage(imageRepository.save(request.getImageCreateRequest().toEntity()));
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
        List<Career> careers = careerRepository.findAllById(request.getCareerIds());
        List<Skill> skills = skillRepository.findAllById(request.getSkillIds());
        user.update(request, profileImage, careers, skills);
        if (user.getUserType() == null) {
            user.setUserType(UserType.MEMBER);
        }
        return userRepository.save(user);
    }

    public User updateProfileImage(Long userId, UserProfileImageUpdateRequest request) {
        Long profileImageId = request.getProfileImageId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        Image profileImage = imageRepository.findById(profileImageId)
                .orElseThrow(() -> new EntityNotFoundException("Profile image not found with id: " + profileImageId));
        user.setProfileImage(profileImage);
        return userRepository.save(user);
    }

    public User updateCareers(Long userId, UserCareersUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        List<Career> careers = careerRepository.findAllById(request.getCareerIds());
        user.setCareers(careers);
        return userRepository.save(user);
    }

    public User updateSkills(Long userId, UserSkillsUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        List<Skill> skills = skillRepository.findAllById(request.getSkillIds());
        user.setSkills(skills);
        return userRepository.save(user);
    }

    public User patchUser(Long id, UserPatchRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        userPatchMapper.updateUserFromDto(request, user);

        if (request.getProfileImageId() != null) {
            Image profileImage = imageRepository.findById(request.getProfileImageId())
                    .orElseThrow(() -> new EntityNotFoundException("Profile image not found with id: " + request.getProfileImageId()));
            user.setProfileImage(profileImage);
        }
        if (request.getCareerIds() != null) {
            List<Career> careers = careerRepository.findAllById(request.getCareerIds());
            user.setCareers(careers);
        }
        if (request.getSkillIds() != null) {
            List<Skill> skills = skillRepository.findAllById(request.getSkillIds());
            user.setSkills(skills);
        }
        if (user.getUserType() == null) {
            user.setUserType(UserType.MEMBER);
        }
        return userRepository.save(user);
    }

    /**
     * cursor based pagination with filter
     */
    public Slice<UserResponse> getUsersByCursor(Long lastId, int pageSize, String nameFilter, UserType userTypeFilter) {
        return userRepository.findAllByCursor(lastId, pageSize, nameFilter, userTypeFilter)
                .map(UserResponse::from);
    }

    @Transactional
    public List<User> createUsers(List<UserCreateRequest> requests) {
        return requests.stream().map(request -> createUser(request)).toList();
    }
}
