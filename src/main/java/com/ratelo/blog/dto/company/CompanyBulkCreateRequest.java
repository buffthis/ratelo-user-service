package com.ratelo.blog.dto.company;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CompanyBulkCreateRequest {
    private List<CompanyCreateRequest> companies;
} 