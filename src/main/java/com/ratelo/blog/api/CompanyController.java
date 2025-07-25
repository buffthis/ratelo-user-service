package com.ratelo.blog.api;

import com.ratelo.blog.domain.company.CompanyService;
import com.ratelo.blog.dto.company.CompanyCreateRequest;
import com.ratelo.blog.dto.company.CompanyResponse;
import com.ratelo.blog.dto.company.CompanyUpdateRequest;
import com.ratelo.blog.dto.company.CompanyBulkCreateRequest;
import com.ratelo.blog.dto.company.CompanyPatchRequest;
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

    @PostMapping("/bulk")
    public List<CompanyResponse> createCompanies(@RequestBody CompanyBulkCreateRequest request) {
        return CompanyResponse.from(companyService.createCompanies(request.getCompanies()));
    }

    @PutMapping("/{id}")
    public CompanyResponse updateCompany(
            @PathVariable Long id,
            @RequestBody CompanyUpdateRequest request) {
        return CompanyResponse.from(companyService.updateCompany(id, request));
    }

    @PatchMapping("/{id}")
    public CompanyResponse patchCompany(
            @PathVariable Long id,
            @RequestBody CompanyPatchRequest request) {
        return CompanyResponse.from(companyService.patchCompany(id, request));
    }
}
