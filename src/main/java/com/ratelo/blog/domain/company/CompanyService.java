package com.ratelo.blog.domain.company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
