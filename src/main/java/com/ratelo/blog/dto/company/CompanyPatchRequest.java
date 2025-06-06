package com.ratelo.blog.dto.company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyPatchRequest {
    private String name;
    private Long logoId;
    private Long wideLogoId;
} 