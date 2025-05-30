package com.ratelo.blog.api.dto;

import com.ratelo.blog.domain.company.Company;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {
    private Long id;
    private String name;
    private ImageResponse logo;

    public static CompanyResponse from(Company company) {
        if (company == null) return null;
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .logo(ImageResponse.from(company.getLogo()))
                .build();
    }

    public static List<CompanyResponse> from(List<Company> companies) {
        if (companies == null) return List.of();
        return companies.stream()
                .map(CompanyResponse::from)
                .collect(Collectors.toList());
    }
}