package com.ratelo.blog.api.dto;

import com.ratelo.blog.domain.career.Career;
import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerCreateRequest {

    @NotNull
    private Long companyId;

    @NotNull
    private String team;

    @NotNull
    private String position;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private String periodNote;

    private String description;

    @NotNull
    private Long userId;

    public Career toEntity(Company company, User user) {
        return Career.builder()
                .company(company)
                .team(team)
                .position(position)
                .startDate(startDate)
                .endDate(endDate)
                .periodNote(periodNote)
                .description(description)
                .user(user)
                .build();
    }
}
