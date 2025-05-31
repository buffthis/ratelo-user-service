package com.ratelo.blog.dto.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateRequest {
    @NotNull
    private String name;
    private Long logoId;
} 