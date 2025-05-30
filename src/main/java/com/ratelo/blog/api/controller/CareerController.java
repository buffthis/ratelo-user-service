package com.ratelo.blog.api.controller;

import com.ratelo.blog.api.dto.CareerResponse;
import com.ratelo.blog.domain.career.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/careers")
@RequiredArgsConstructor
public class CareerController {

    private final CareerService careerService;

    @GetMapping("/{id}")
    public CareerResponse getCareerById(@PathVariable Long id) {
        return CareerResponse.from(careerService.getCareerById(id));
    }

    @GetMapping
    public List<CareerResponse> getAllCareers() {
        return CareerResponse.from(careerService.getAllCareers());
    }
}
