package com.ratelo.blog.domain.career;

import com.ratelo.blog.api.dto.CareerCreateRequest;
import com.ratelo.blog.api.dto.CareerUpdateRequest;
import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.domain.company.CompanyRepository;
import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public Career getCareerById(Long id) {
        return careerRepository.findById(id).orElse(null);
    }

    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    public Career createCareer(CareerCreateRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + request.getCompanyId()));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));
        return careerRepository.save(request.toEntity(company, user)); 
    }

    public Career updateCareer(Long id, CareerUpdateRequest request) {
        Career career = careerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Career not found with id: " + id));

        Company company = companyRepository.findById(request.getCompanyId())
            .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + request.getCompanyId()));

        career.update(request, company);
        return careerRepository.save(career);
    }
}
