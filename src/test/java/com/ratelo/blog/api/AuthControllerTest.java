package com.ratelo.blog.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratelo.blog.dto.user.UserCreateRequest;
import com.ratelo.blog.dto.user.UserLoginRequest;
import com.ratelo.blog.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        // 테스트용 유저 생성 (비밀번호는 반드시 평문으로 넣고, 서비스에서 암호화됨)
        UserCreateRequest request = new UserCreateRequest();
        request.setUsername("loginuser");
        request.setName("로그인유저");
        request.setPassword("testpw");
        // 회원가입 API를 직접 호출해도 되고, UserService를 주입받아 호출해도 됩니다.
        // 여기서는 간단히 UserService를 사용하는 것이 더 안전합니다.
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        // 먼저 회원가입
        UserCreateRequest signup = new UserCreateRequest();
        signup.setUsername("loginuser");
        signup.setName("로그인유저");
        signup.setPassword("testpw");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signup)))
                .andExpect(status().isOk());

        // 로그인 시도
        UserLoginRequest login = new UserLoginRequest();
        login.setUsername("loginuser");
        login.setPassword("testpw");
        mockMvc.perform(post("/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 비밀번호")
    void loginFail_wrongPassword() throws Exception {
        // 회원가입
        UserCreateRequest signup = new UserCreateRequest();
        signup.setUsername("loginuser2");
        signup.setName("로그인유저2");
        signup.setPassword("testpw");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signup)))
                .andExpect(status().isOk());

        // 잘못된 비밀번호로 로그인 시도
        UserLoginRequest login = new UserLoginRequest();
        login.setUsername("loginuser2");
        login.setPassword("wrongpw");
        mockMvc.perform(post("/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그아웃 성공")
    void logoutSuccess() throws Exception {
        // 로그아웃은 세션이 없어도 항상 200 OK 반환
        mockMvc.perform(delete("/sessions"))
                .andExpect(status().isOk());
    }
}