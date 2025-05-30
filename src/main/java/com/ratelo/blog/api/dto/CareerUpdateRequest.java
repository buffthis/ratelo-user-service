package com.ratelo.blog.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerUpdateRequest {

    @NotNull
    private Long companyId;

    @NotNull
    private String team;

    @NotNull
    private String position;

    @NotNull
    private String name;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private String periodNote;

    private String description;

    @NotNull
    private Long userId;
}