package com.ratelo.blog.domain.career;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CareerRepositoryImplTest {

    @Autowired
    private CareerRepository careerRepository;

    @BeforeEach
    void setUp() {
        Career visibleCareer = Career.builder()
                .team("visibleTeam")
                .position("visiblePosition")
                .hidden(false)
                .build();
        Career hiddenCareer = Career.builder()
                .team("hiddenTeam")
                .position("hiddenPosition")
                .hidden(true)
                .build();
        careerRepository.save(visibleCareer);
        careerRepository.save(hiddenCareer);
    }

    @Test
    @DisplayName("기본적으로 hidden=false인 데이터만 조회된다")
    void should_ReturnOnlyVisibleCareers_When_IncludeHiddenIsFalseOrNull() {
        List<Career> result1 = careerRepository.findAllByCondition(false, null);
        List<Career> result2 = careerRepository.findAllByCondition(null, null);
        assertThat(result1).hasSize(1);
        assertThat(result1.get(0).getTeam()).isEqualTo("visibleTeam");
        assertThat(result2).hasSize(1);
        assertThat(result2.get(0).getTeam()).isEqualTo("visibleTeam");
    }

    @Test
    @DisplayName("includeHidden=true이면 모든 데이터가 조회된다")
    void should_ReturnAllCareers_When_IncludeHiddenIsTrue() {
        List<Career> result = careerRepository.findAllByCondition(true, null);
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Career::getTeam).containsExactlyInAnyOrder("visibleTeam", "hiddenTeam");
    }
}