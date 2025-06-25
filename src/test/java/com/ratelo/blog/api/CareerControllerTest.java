package com.ratelo.blog.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratelo.blog.domain.career.Career;
import com.ratelo.blog.domain.career.CareerRepository;
import com.ratelo.blog.dto.career.CareerPatchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
class CareerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Career savedCareer;

    @BeforeEach
    void setUp() {
        Career career = Career.builder()
                .team("testTeam")
                .position("testPosition")
                .build();
        savedCareer = careerRepository.save(career);
    }

    @Test
    void should_UpdatePositionField_When_PatchRequestContainsPosition() throws Exception {
        CareerPatchRequest patchRequest = new CareerPatchRequest();
        patchRequest.setPosition("updatedPosition");

        mockMvc.perform(patch("/careers/{id}", savedCareer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedCareer.getId()))
                .andExpect(jsonPath("$.team").value("testTeam"))
                .andExpect(jsonPath("$.position").value("updatedPosition"));

        Career updated = careerRepository.findById(savedCareer.getId()).orElseThrow();
        assertThat(updated.getTeam()).isEqualTo("testTeam");
        assertThat(updated.getPosition()).isEqualTo("updatedPosition");
    }

    @Test
    void should_UpdateMultipleFields_When_PatchRequestContainsMultipleFields() throws Exception {
        CareerPatchRequest patchRequest = new CareerPatchRequest();
        patchRequest.setTeam("updatedTeam");
        patchRequest.setPosition("updatedPosition");
        patchRequest.setHidden(true);

        mockMvc.perform(patch("/careers/{id}", savedCareer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.team").value("updatedTeam"))
                .andExpect(jsonPath("$.position").value("updatedPosition"));

        Career updated = careerRepository.findById(savedCareer.getId()).orElseThrow();
        assertThat(updated.getTeam()).isEqualTo("updatedTeam");
        assertThat(updated.getPosition()).isEqualTo("updatedPosition");
        assertThat(updated.isHidden()).isTrue();
    }
}