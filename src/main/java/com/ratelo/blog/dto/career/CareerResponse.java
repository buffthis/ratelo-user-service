package com.ratelo.blog.dto.career;

import com.ratelo.blog.domain.career.Career;
import com.ratelo.blog.dto.company.CompanyResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareerResponse {
    private Long id;
    private CompanyResponse company;
    private String team;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String periodNote;
    private String description;
    private Boolean hidden;
    private Boolean masked;

    public static CareerResponse from(Career career) {
        return CareerResponse.builder()
                .id(career.getId())
                .company(
                    career.isMasked()
                    ? CompanyResponse.builder()
                        .id(null)
                        .name("***********")
                        .logo(null)
                        .wideLogo(null)
                        .build()
                    : CompanyResponse.from(career.getCompany())
                )
                .team(career.getTeam())
                .position(career.getPosition())
                .startDate(career.getStartDate())
                .endDate(career.getEndDate())
                .periodNote(career.getPeriodNote())
                .description(career.getDescription())
                .hidden(career.isHidden())
                .masked(career.isMasked())
                .build();
    }

    public static List<CareerResponse> from(List<Career> careers) {
        if (careers == null) return List.of();
        return careers.stream()
                .map(CareerResponse::from)
                .collect(Collectors.toList());
    }
}