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
            user.setUserType(UserType.TEST);
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

    public String getUserSvgCard(String username) {
        User user = getUserByUsername(username);
        Long id = user.getId();
        if (user == null) throw new EntityNotFoundException("User not found with username: " + username);

        String profileImageUrl = user.getProfileImage() != null ? user.getProfileImage().getUrl() : null;
        String name = user.getName() != null ? user.getName() : "";
        String bio = user.getBio() != null ? user.getBio() : "";

        List<Career> careers = careerRepository.findAllByUserId(id);
        com.ratelo.blog.domain.company.Company company = null;
        if (careers != null && !careers.isEmpty()) {
            careers = careers.stream()
                .filter(c -> c.getCompany() != null && c.getCompany().getLogo() != null && !c.isHidden())
                .sorted((a, b) -> {
                    if (a.getStartDate() == null && b.getStartDate() == null) return 0;
                    if (a.getStartDate() == null) return 1;
                    if (b.getStartDate() == null) return -1;
                    return b.getStartDate().compareTo(a.getStartDate());
                })
                .toList();
            if (!careers.isEmpty()) {
                company = careers.get(0).getCompany();
            }
        }
        String companyLogoUrl = null;
        if (company != null) {
            if (company.getWideLogo() != null) {
                companyLogoUrl = company.getWideLogo().getUrl();
            } else if (company.getLogo() != null) {
                companyLogoUrl = company.getLogo().getUrl();
            }
        }
        StringBuilder svg = new StringBuilder();
        svg.append("<svg width=\"160\" height=\"204\" viewBox=\"0 0 160 204\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
        svg.append(String.format("<a xlink:href=\"https://unblind.kr/users/%s\" target=\"_blank\">", escapeXml(user.getUsername())));
        svg.append("<defs><clipPath id=\"profileClip\"><circle cx=\"80\" cy=\"48\" r=\"28\"/></clipPath></defs>");
        svg.append("<rect x=\"0\" y=\"0\" width=\"160\" height=\"204\" rx=\"16\" fill=\"#fff\" stroke=\"#e0e0e0\" stroke-width=\"1.5\"/>");
        svg.append("<circle cx=\"80\" cy=\"48\" r=\"28\" fill=\"#f3f4f6\" stroke=\"#e0e0e0\" stroke-width=\"1.5\"/>");
        if (profileImageUrl != null) {
            svg.append(String.format("<image xlink:href=\"%s\" x=\"52\" y=\"20\" width=\"56\" height=\"56\" clip-path=\"url(#profileClip)\" />", profileImageUrl));
        }
        svg.append(String.format("<text x=\"80\" y=\"90\" font-size=\"14\" font-weight=\"600\" fill=\"#222\" text-anchor=\"middle\" alignment-baseline=\"middle\" font-family=\"Pretendard, Noto Sans KR, Arial, sans-serif\">%s</text>", escapeXml(user.getUsername())));
        svg.append(String.format("<text x=\"80\" y=\"110\" font-size=\"12\" font-weight=\"300\" fill=\"#222\" text-anchor=\"middle\" alignment-baseline=\"middle\" font-family=\"Pretendard, Noto Sans KR, Arial, sans-serif\">%s</text>", escapeXml(name)));
        svg.append(String.format("<text x=\"80\" y=\"128\" font-size=\"10\" font-weight=\"300\" fill=\"#888\" text-anchor=\"middle\" alignment-baseline=\"middle\" font-family=\"Pretendard, Noto Sans KR, Arial, sans-serif\">%s</text>", escapeXml(bio)));
        svg.append("<rect x=\"20\" y=\"146\" width=\"120\" height=\"1.5\" fill=\"#e0e0e0\" rx=\"0.75\"/>");
        if (companyLogoUrl != null) {
            svg.append(String.format("<image xlink:href=\"%s\" x=\"30\" y=\"156\" width=\"100\" height=\"32\" />", companyLogoUrl));
        }
        svg.append("</a>");
        svg.append("</svg>");
        return svg.toString();
    }

    private static String escapeXml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&apos;");
    }
}
