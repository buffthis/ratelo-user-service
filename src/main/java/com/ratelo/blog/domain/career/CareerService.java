package com.ratelo.blog.domain.career;

import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.domain.company.CompanyRepository;
import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.domain.user.UserRepository;
import com.ratelo.blog.dto.career.CareerCreateRequest;
import com.ratelo.blog.dto.career.CareerUpdateRequest;
import com.ratelo.blog.dto.career.CareerPatchRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Autowired
    private CareerPatchMapper careerPatchMapper;

    public Career getCareerById(Long id) {
        return careerRepository.findById(id).orElse(null);
    }

    public List<Career> getAllCareers(Boolean includeHidden, Long userId, String username) {
        return careerRepository.findAllByCondition(includeHidden, userId, username);
    }

    public List<Career> createCareers(List<CareerCreateRequest> requests) {
        List<Career> careers = requests.stream().map(request -> {
            Career career = request.toEntity();
            Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + request.getCompanyId()));
            career.setCompany(company);
            User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));
            career.setUser(user);
            return career;
        }).toList();
        return careerRepository.saveAll(careers);
    }

    public Career updateCareer(Long id, CareerUpdateRequest request) {
        Career career = careerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Career not found with id: " + id));

        Company company = companyRepository.findById(request.getCompanyId())
            .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + request.getCompanyId()));

        career.update(request, company);
        return careerRepository.save(career);
    }

    public Career patchCareer(Long id, CareerPatchRequest request) {
        Career career = careerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Career not found with id: " + id));

        careerPatchMapper.updateCareerFromDto(request, career);

        if (request.getCompanyId() != null) {
            Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + request.getCompanyId()));
            career.setCompany(company);
        }
        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));
            career.setUser(user);
        }
        return careerRepository.save(career);
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
}
