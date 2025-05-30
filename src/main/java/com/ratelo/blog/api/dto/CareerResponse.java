package com.ratelo.blog.api.dto;

import com.ratelo.blog.domain.career.Career;
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
    private String description;


    public static CareerResponse from(Career career) {
        return CareerResponse.builder()
                .id(career.getId())
                .company(CompanyResponse.from(career.getCompany()))
                .team(career.getTeam())
                .position(career.getPosition())
                .startDate(career.getStartDate())
                .endDate(career.getEndDate())
                .description(career.getDescription())
                .build();
    }

    public static List<CareerResponse> from(List<Career> careers) {
        if (careers == null) return List.of();
        return careers.stream()
                .map(CareerResponse::from)
                .collect(Collectors.toList());
    }
}