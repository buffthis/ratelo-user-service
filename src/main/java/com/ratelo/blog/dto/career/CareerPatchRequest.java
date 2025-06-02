package com.ratelo.blog.dto.career;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

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
    private Boolean isHidden;
} 