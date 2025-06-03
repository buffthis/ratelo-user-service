package com.ratelo.blog.dto.career;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CareerBulkCreateRequest {
    private List<CareerCreateRequest> careers;
} 