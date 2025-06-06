package com.ratelo.blog.dto.company;

import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.dto.image.ImageResponse;
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
    private ImageResponse wideLogo;

    public static CompanyResponse from(Company company) {
        if (company == null) return null;
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .logo(ImageResponse.from(company.getLogo()))
                .wideLogo(ImageResponse.from(company.getWideLogo()))
                .build();
    }

    public static List<CompanyResponse> from(List<Company> companies) {
        if (companies == null) return List.of();
        return companies.stream()
                .map(CompanyResponse::from)
                .collect(Collectors.toList());
    }
}