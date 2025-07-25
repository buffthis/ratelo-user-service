package com.ratelo.blog.dto.career;

import com.ratelo.blog.domain.career.Career;
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

    private Boolean hidden;

    @NotNull
    private Long userId;

    public Career toEntity() {
        return Career.builder()
                .team(team)
                .position(position)
                .startDate(startDate)
                .endDate(endDate)
                .periodNote(periodNote)
                .description(description)
                .hidden(hidden)
                .build();
    }
}
