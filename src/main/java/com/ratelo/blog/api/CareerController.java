package com.ratelo.blog.api;

import com.ratelo.blog.domain.career.Career;
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
    public Career getCareer(@PathVariable Long id) {
        return careerService.getCareerById(id);
    }

    @GetMapping
    public List<Career> getAllCareers() {
        return careerService.getAllCareers();
    }
}
