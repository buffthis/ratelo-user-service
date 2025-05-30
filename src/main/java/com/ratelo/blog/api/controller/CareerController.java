package com.ratelo.blog.api.controller;

import com.ratelo.blog.api.dto.CareerCreateRequest;
import com.ratelo.blog.api.dto.CareerResponse;
import com.ratelo.blog.api.dto.CareerUpdateRequest;
import com.ratelo.blog.domain.career.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public CareerResponse createCareer(@RequestBody CareerCreateRequest request) {
        return CareerResponse.from(careerService.createCareer(request));
    }

    @PutMapping("/{id}")
    public CareerResponse updateCareer(
            @PathVariable Long id,
            @RequestBody CareerUpdateRequest request) {
        return CareerResponse.from(careerService.updateCareer(id, request));
    }
}
