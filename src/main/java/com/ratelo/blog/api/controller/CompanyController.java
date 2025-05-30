package com.ratelo.blog.api.controller;

import com.ratelo.blog.api.dto.CompanyResponse;
import com.ratelo.blog.domain.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/{id}")
    public CompanyResponse getCompanyById(@PathVariable Long id) {
        return CompanyResponse.from(companyService.getCompanyById(id));
    }

    @GetMapping
    public List<CompanyResponse> getAllCompanies() {
        return CompanyResponse.from(companyService.getAllCompanies());
    }
}
