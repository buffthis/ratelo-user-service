package com.ratelo.blog.api;

import com.ratelo.blog.domain.career.CareerService;
import com.ratelo.blog.dto.career.CareerCreateRequest;
import com.ratelo.blog.dto.career.CareerPatchRequest;
import com.ratelo.blog.dto.career.CareerResponse;
import com.ratelo.blog.dto.career.CareerUpdateRequest;
import com.ratelo.blog.dto.career.CareerBulkCreateRequest;
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
    public List<CareerResponse> getAllCareers(
            @RequestParam(required = false) Boolean includeHidden,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String username) {
        return CareerResponse.from(careerService.getAllCareers(includeHidden, userId, username));
    }

    @PostMapping
    public CareerResponse createCareer(@RequestBody CareerCreateRequest request) {
        return CareerResponse.from(careerService.createCareer(request));
    }

    @PostMapping("/bulk")
    public List<CareerResponse> createCareers(@RequestBody CareerBulkCreateRequest request) {
        return CareerResponse.from(careerService.createCareers(request.getCareers()));
    }

    @PutMapping("/{id}")
    public CareerResponse updateCareer(
            @PathVariable Long id,
            @RequestBody CareerUpdateRequest request) {
        return CareerResponse.from(careerService.updateCareer(id, request));
    }

    @PatchMapping("/{id}")
    public CareerResponse patchCareer(@PathVariable Long id, @RequestBody CareerPatchRequest request) {
        return CareerResponse.from(careerService.patchCareer(id, request));
    }
}
