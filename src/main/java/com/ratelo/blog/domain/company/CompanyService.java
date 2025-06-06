package com.ratelo.blog.domain.company;

import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.image.ImageRepository;
import com.ratelo.blog.dto.company.CompanyCreateRequest;
import com.ratelo.blog.dto.company.CompanyUpdateRequest;
import com.ratelo.blog.dto.company.CompanyPatchRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ImageRepository imageRepository;
    private final CompanyPatchMapper companyPatchMapper;

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company createCompany(CompanyCreateRequest request) {
        Company company = request.toEntity();

        Image logo = imageRepository.findById(request.getLogoId())
            .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
        company.setLogo(logo);

        if (request.getWideLogoId() != null) {
            Image wideLogo = imageRepository.findById(request.getWideLogoId())
                    .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
            company.setWideLogo(wideLogo);
        }

        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, CompanyUpdateRequest request) {
        Company company = companyRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));
        Image logo = imageRepository.findById(request.getLogoId())
            .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
        Image wideLogo = null;
        if (request.getWideLogoId() != null) {
            wideLogo = imageRepository.findById(request.getLogoId())
                    .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
        }
        company.update(request, logo, wideLogo);
        return companyRepository.save(company);
    }

    public List<Company> createCompanies(List<CompanyCreateRequest> requests) {
        List<Company> companies = requests.stream().map(request -> {
            Company company = request.toEntity();
            Image logo = imageRepository.findById(request.getLogoId())
                .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
            company.setLogo(logo);
            if (request.getWideLogoId() != null) {
                Image wideLogo = imageRepository.findById(request.getWideLogoId())
                        .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
                company.setWideLogo(wideLogo);
            }
            return company;
        }).toList();
        return companyRepository.saveAll(companies);
    }

    public Company patchCompany(Long id, CompanyPatchRequest request) {
        Company company = companyRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id));

        companyPatchMapper.updateCompanyFromDto(request, company);

        if (request.getLogoId() != null) {
            Image logo = imageRepository.findById(request.getLogoId())
                .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
            company.setLogo(logo);
        }
        if (request.getWideLogoId() != null) {
            Image wideLogo = imageRepository.findById(request.getWideLogoId())
                .orElseThrow(() -> new EntityNotFoundException("Wide logo image not found with id: " + request.getWideLogoId()));
            company.setWideLogo(wideLogo);
        }
        return companyRepository.save(company);
    }
}
