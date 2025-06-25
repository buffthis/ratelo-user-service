package com.ratelo.blog.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratelo.blog.dto.post.PostPatchRequest;
import com.ratelo.blog.domain.post.Post;
import com.ratelo.blog.domain.post.PostRepository;
import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.domain.user.UserRepository;
import com.ratelo.blog.domain.user.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private Post savedPost;
    private User savedUser;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        savedUser = userRepository.save(User.builder()
                .username("testuser")
                .name("testuser")
                .userType(UserType.TEST)
                .password("testpassword")
                .build());
        savedPost = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .createdAt(LocalDateTime.now())
                .hidden(false)
                .user(savedUser)
                .build());
    }

    @Test
    void should_UpdateTitleField_When_PatchRequestContainsTitle() throws Exception {
        PostPatchRequest req = new PostPatchRequest();
        req.setTitle("new title");
        mockMvc.perform(patch("/posts/" + savedPost.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("new title"))
                .andExpect(jsonPath("$.content").value("content"));
    }
}