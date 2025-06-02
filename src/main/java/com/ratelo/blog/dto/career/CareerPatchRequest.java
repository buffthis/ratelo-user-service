package com.ratelo.blog.dto.career;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CareerPatchRequest {
    private Long companyId;
    private String team;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String periodNote;
    private String description;
    private Long userId;
    private Boolean hidden;
    private Boolean masked;
}