package com.ratelo.blog.api.controller;

import com.ratelo.blog.api.dto.CompanyCreateRequest;
import com.ratelo.blog.api.dto.CompanyResponse;
import com.ratelo.blog.api.dto.CompanyUpdateRequest;
import com.ratelo.blog.domain.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public CompanyResponse createCompany(@RequestBody CompanyCreateRequest request) {
        return CompanyResponse.from(companyService.createCompany(request));
    }

    @PutMapping("/{id}")
    public CompanyResponse updateCompany(
            @PathVariable Long id,
            @RequestBody CompanyUpdateRequest request) {
        return CompanyResponse.from(companyService.updateCompany(id, request));
    }
}
