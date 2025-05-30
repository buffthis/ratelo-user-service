package com.ratelo.blog.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateRequest {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    private String description;
    private Long logoId;
} 