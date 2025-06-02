package com.ratelo.blog.domain.career;

import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.domain.company.CompanyRepository;
import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.domain.user.UserRepository;
import com.ratelo.blog.dto.career.CareerCreateRequest;
import com.ratelo.blog.dto.career.CareerUpdateRequest;
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
        return careerRepository.findAllWithHiddenOption(false);
    }

    public Career createCareer(CareerCreateRequest request) {
        Career career = request.toEntity();

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + request.getCompanyId()));
        career.setCompany(company);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));
        career.setUser(user);

        return careerRepository.save(career);
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
