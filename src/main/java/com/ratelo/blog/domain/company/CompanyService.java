package com.ratelo.blog.domain.company;

import com.ratelo.blog.api.dto.CompanyCreateRequest;
import com.ratelo.blog.api.dto.CompanyUpdateRequest;
import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.image.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ImageRepository imageRepository;

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company createCompany(CompanyCreateRequest request) {
        Image logo = imageRepository.findById(request.getLogoId())
            .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
        return companyRepository.save(request.toEntity(logo));
    }

    public Company updateCompany(Long id, CompanyUpdateRequest request) {
        Company company = companyRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));
        Image logo = imageRepository.findById(request.getLogoId())
            .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
        company.update(request, logo);
        return companyRepository.save(company);
    }
}
