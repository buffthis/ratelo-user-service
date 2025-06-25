package com.ratelo.blog.domain.user;

import com.ratelo.blog.domain.career.Career;
import com.ratelo.blog.domain.career.CareerRepository;
import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.image.ImageRepository;
import com.ratelo.blog.domain.skill.Skill;
import com.ratelo.blog.domain.skill.SkillRepository;
import com.ratelo.blog.dto.user.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CareerRepository careerRepository;
    private final ImageRepository imageRepository;
    private final SkillRepository skillRepository;
    private final UserPatchMapper userPatchMapper;
    private final PasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
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
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
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
        svg.append(String.format("<a xlink:href=\"https://unblind.kr/%s\" target=\"_blank\">", escapeXml(user.getUsername())));
        svg.append("<defs><clipPath id=\"profileClip\"><circle cx=\"80\" cy=\"48\" r=\"28\"/></clipPath></defs>");
        svg.append("<rect x=\"0\" y=\"0\" width=\"160\" height=\"204\" rx=\"16\" fill=\"#fff\" stroke=\"#e0e0e0\" stroke-width=\"1.5\"/>");
        svg.append("<circle cx=\"80\" cy=\"48\" r=\"28\" fill=\"#f3f4f6\" stroke=\"#e0e0e0\" stroke-width=\"1.5\"/>");
        if (profileImageUrl != null) {
            svg.append(String.format("<image href=\"%s\" x=\"52\" y=\"20\" width=\"56\" height=\"56\" clip-path=\"url(#profileClip)\" />", profileImageUrl));
        }
        svg.append(String.format("<text x=\"80\" y=\"90\" font-size=\"14\" font-weight=\"600\" fill=\"#222\" text-anchor=\"middle\" alignment-baseline=\"middle\" font-family=\"Pretendard, Noto Sans KR, Arial, sans-serif\">%s</text>", escapeXml(user.getUsername())));
        svg.append(String.format("<text x=\"80\" y=\"110\" font-size=\"12\" font-weight=\"300\" fill=\"#222\" text-anchor=\"middle\" alignment-baseline=\"middle\" font-family=\"Pretendard, Noto Sans KR, Arial, sans-serif\">%s</text>", escapeXml(name)));
        svg.append(String.format("<text x=\"80\" y=\"128\" font-size=\"10\" font-weight=\"300\" fill=\"#888\" text-anchor=\"middle\" alignment-baseline=\"middle\" font-family=\"Pretendard, Noto Sans KR, Arial, sans-serif\">%s</text>", escapeXml(bio)));
        svg.append("<rect x=\"20\" y=\"146\" width=\"120\" height=\"1.5\" fill=\"#e0e0e0\" rx=\"0.75\"/>");
        if (companyLogoUrl != null) {
            svg.append(String.format("<image href=\"%s\" x=\"30\" y=\"156\" width=\"100\" height=\"32\" />", companyLogoUrl));
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

    public byte[] generateUserPngCard(String username) throws Exception {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
        String profileImageUrl = user.getProfileImage() != null ? user.getProfileImage().getUrl() : null;
        String name = user.getName();
        String bio = user.getBio();

        // 최신 커리어에서 회사 로고 추출
        List<Career> careers = careerRepository.findAllByUserId(user.getId());
        Career latestCareer = careers.stream()
                .filter(c -> !c.isHidden() && c.getCompany() != null)
                .sorted(java.util.Comparator.comparing(Career::getStartDate, java.util.Comparator.nullsLast(java.util.Comparator.reverseOrder())))
                .findFirst()
                .orElse(null);
        String companyLogoUrl = null;
        if (latestCareer != null) {
            Company company = latestCareer.getCompany();
            if (company.getWideLogo() != null) {
                companyLogoUrl = company.getWideLogo().getUrl();
            } else {
                companyLogoUrl = company.getLogo().getUrl();
            }
        }

        // PNG 카드 이미지 생성 (CardPngGenerator.generateCardInternal 내용 직접 구현)
        int width = 960, height = 1140;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 배경
        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, width, height, 96, 96);
        g.setColor(new Color(224, 224, 224));
        g.setStroke(new BasicStroke(9f));
        g.drawRoundRect(4, 4, width-8, height-8, 96, 96);

        // 프로필 이미지 (원형)
        if (profileImageUrl != null) {
            try {
                BufferedImage profile = fetchImageWithUserAgent(profileImageUrl);
                Shape oldClip = g.getClip();
                g.setClip(new Ellipse2D.Float(312, 120, 336, 336));
                g.drawImage(profile, 312, 120, 336, 336, null);
                g.setClip(oldClip);
                g.setColor(new Color(224, 224, 224));
                g.setStroke(new BasicStroke(9f));
                g.drawOval(312, 120, 336, 336);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // username
        g.setColor(new Color(34, 34, 34));
        g.setFont(new Font("SansSerif", Font.BOLD, 64));
        drawCenteredString(g, username, width, 540, g);
        // name
        g.setFont(new Font("SansSerif", Font.PLAIN, 72));
        drawCenteredString(g, name, width, 640, g);
        // bio
        g.setColor(new Color(136, 136, 136));
        g.setFont(new Font("SansSerif", Font.PLAIN, 48));
        drawCenteredString(g, bio, width, 720, g);

        // Divider (두께 절반)
        g.setColor(new Color(224, 224, 224));
        g.fillRoundRect(96, 780, 768, 3, 6, 6);

        // 회사 로고
        if (companyLogoUrl != null) {
            try {
                BufferedImage logo = fetchImageWithUserAgent(companyLogoUrl);
                int maxLogoWidth = 600;
                int maxLogoHeight = 180;

                int logoWidth = logo.getWidth();
                int logoHeight = logo.getHeight();

                double widthScale = (double) maxLogoWidth / logoWidth;
                double heightScale = (double) maxLogoHeight / logoHeight;
                double scale = Math.min(widthScale, heightScale);

                int targetWidth = (int) (logoWidth * scale);
                int targetHeight = (int) (logoHeight * scale);

                int areaX = 180, areaWidth = 600;
                int logoX = areaX + (areaWidth - targetWidth) / 2;

                int dividerBottomY = 780;
                int cardBottomY = height;
                int logoY = (dividerBottomY + cardBottomY) / 2 - targetHeight / 2;

                g.drawImage(logo, logoX, logoY, targetWidth, targetHeight, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        g.dispose();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        return out.toByteArray();
    }

    private static void drawCenteredString(Graphics2D g, String text, int width, int y, Graphics2D context) {
        if (text == null) text = "";
        FontMetrics fm = context.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        context.drawString(text, x, y);
    }

    private static BufferedImage fetchImageWithUserAgent(String url) throws Exception {
        java.net.URL u = new java.net.URL(url);
        java.net.URLConnection conn = u.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        try (InputStream in = conn.getInputStream()) {
            return ImageIO.read(in);
        }
    }
}
