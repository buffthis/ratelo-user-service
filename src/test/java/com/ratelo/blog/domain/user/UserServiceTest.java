package com.ratelo.blog.domain.user;

import com.ratelo.blog.domain.career.Career;
import com.ratelo.blog.domain.career.CareerRepository;
import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.domain.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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
    @DisplayName("success")
    void testGetUserSvgCard_success() {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("tester");
        user.setName("tester");
        user.setBio("tester");
        Image profileImage = new Image();
        profileImage.setUrl("https://img.com/profile.png");
        user.setProfileImage(profileImage);

        Company company = new Company();
        Image logo = new Image();
        logo.setUrl("https://img.com/logo.png");
        company.setLogo(logo);

        Career career = new Career();
        career.setCompany(company);
        career.setStartDate(LocalDate.of(2022, 1, 1));
        career.setHidden(false);

        when(userRepository.findUserByUsername("tester")).thenReturn(Optional.of(user));
        when(careerRepository.findAllByUserId(1L)).thenReturn(List.of(career));

        // when
        String svg = userService.getUserSvgCard("tester");

        // then
        assertTrue(svg.contains("tester"));
        assertTrue(svg.contains("tester"));
        assertTrue(svg.contains("tester"));
        assertTrue(svg.contains("https://img.com/profile.png"));
        assertTrue(svg.contains("https://img.com/logo.png"));
        assertTrue(svg.startsWith("<svg"));
        assertTrue(svg.endsWith("</svg>"));
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

        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(careerRepository.findAllByUserId(10L)).thenReturn(List.of(career));

        // when
        String svg = userService.getUserSvgCard("parrot");

        // then: save svg file
        java.nio.file.Path outputPath = java.nio.file.Path.of("build/test-output/user_svg_sample.svg");
        java.nio.file.Files.createDirectories(outputPath.getParent());
        java.nio.file.Files.writeString(outputPath, svg, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
    }
}