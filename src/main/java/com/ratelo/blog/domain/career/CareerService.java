package com.ratelo.blog.domain.career;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;

    public Career getCareerById(Long id) {
        return careerRepository.findById(id).orElse(null);
    }

    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }
}
