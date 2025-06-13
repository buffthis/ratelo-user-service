package com.ratelo.blog.domain.user;

import com.ratelo.blog.domain.career.Career;
import com.ratelo.blog.domain.career.CareerRepository;
import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.util.SvgToPngUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CareerRepository careerRepository;
    @Mock
    private com.ratelo.blog.domain.image.ImageRepository imageRepository;
    @Mock
    private com.ratelo.blog.domain.skill.SkillRepository skillRepository;
    @Mock
    private UserPatchMapper userPatchMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("no career or logo")
    void testGetUserSvgCard_noCareerOrLogo() {
        User user = new User();
        user.setId(3L);
        user.setUsername("no-career");
        user.setName("no-career");
        user.setBio("");
        user.setProfileImage(null);
        when(userRepository.findUserByUsername("no-career")).thenReturn(Optional.of(user));
        when(careerRepository.findAllByUserId(3L)).thenReturn(List.of());

        String svg = userService.getUserSvgCard("no-career");
        assertTrue(svg.contains("no-career"));
        assertTrue(svg.contains("no-career"));
        assertTrue(svg.startsWith("<svg"));
        assertTrue(svg.endsWith("</svg>"));
    }

    @Disabled
    @Test
    @DisplayName("save svg file")
    void testSvgFileOutput() throws Exception {
        // given
        User user = new User();
        user.setId(10L);
        user.setUsername("parrot");
        user.setName("앵무새");
        user.setBio("Chef");
        Image profileImage = new Image();
        profileImage.setUrl("https://imgur.com/HOtRUW2.png");
        user.setProfileImage(profileImage);

        Company company = new Company();
        Image logo = new Image();
        logo.setUrl("https://imgur.com/8E8gCQc.png");
        company.setLogo(logo);

        Career career = new Career();
        career.setCompany(company);
        career.setStartDate(LocalDate.of(2023, 1, 1));
        career.setHidden(false);

        when(userRepository.findUserByUsername("parrot")).thenReturn(Optional.of(user));
        when(careerRepository.findAllByUserId(10L)).thenReturn(List.of(career));

        // when
        String svg = userService.getUserSvgCard("parrot");

        // then: save svg file
        java.nio.file.Path outputPath = java.nio.file.Path.of("build/test-output/user_svg_sample.svg");
        java.nio.file.Files.createDirectories(outputPath.getParent());
        java.nio.file.Files.writeString(outputPath, svg, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Disabled
    @Test
    @DisplayName("convert SVG to PNG")
    void testSvgToPngFileOutput() throws Exception {
        // given
        User user = new User();
        user.setId(20L);
        user.setUsername("parrot");
        user.setName("앵무새");
        user.setBio("Chef");
        Image profileImage = new Image();
        profileImage.setUrl("https://imgur.com/HOtRUW2.png");
        user.setProfileImage(profileImage);

        Company company = new Company();
        Image logo = new Image();
        logo.setUrl("https://imgur.com/8E8gCQc.png");
        company.setLogo(logo);

        Career career = new Career();
        career.setCompany(company);
        career.setStartDate(LocalDate.of(2023, 1, 1));
        career.setHidden(false);

        when(userRepository.findUserByUsername("pngfile")).thenReturn(Optional.of(user));
        when(careerRepository.findAllByUserId(20L)).thenReturn(List.of(career));

        // when
        String svg = userService.getUserSvgCard("pngfile");
        byte[] png = SvgToPngUtil.svgToPng(svg);

        // then: 파일로 저장
        java.nio.file.Path outputPath = java.nio.file.Path.of("build/test-output/user_card_sample.png");
        java.nio.file.Files.createDirectories(outputPath.getParent());
        java.nio.file.Files.write(outputPath, png);
        // 파일을 직접 열어서 PNG 이미지를 확인할 수 있습니다.
    }

    @Test
    @DisplayName("UserService로 PNG 카드 이미지를 생성하고 파일로 저장한다")
    void testGenerateUserPngCardFile() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("parrot");
        user.setName("앵무새");
        user.setBio("Chef");
        Image profileImage = new Image();
        profileImage.setUrl("https://unblind-kr-media.s3.ap-northeast-2.amazonaws.com/image/profile-image/shark.png");
        user.setProfileImage(profileImage);

        Company company = new Company();
        Image wideLogo = new Image();
        wideLogo.setUrl("https://unblind-kr-media.s3.ap-northeast-2.amazonaws.com/image/company-logo/neubility_wide_logo.png");
        company.setWideLogo(wideLogo);
        Career career = new Career();
        career.setCompany(company);
        career.setHidden(false);
        career.setStartDate(java.time.LocalDate.of(2023, 1, 1));

        when(userRepository.findUserByUsername("parrot")).thenReturn(Optional.of(user));
        when(careerRepository.findAllByUserId(1L)).thenReturn(List.of(career));

        // when
        byte[] png = userService.generateUserPngCard("parrot");

        // then
        assertNotNull(png, "PNG 생성 결과가 null입니다.");
        Path outputPath = Path.of("build/test-output/card_png_sample.png");
        Files.createDirectories(outputPath.getParent());
        Files.write(outputPath, png);
    }
}