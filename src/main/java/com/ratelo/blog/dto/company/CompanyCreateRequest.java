package com.ratelo.blog.dto.company;

import com.ratelo.blog.domain.company.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCreateRequest {
    @NotNull
    private String name;
    private Long logoId;

    public Company toEntity() {
        return Company.builder()
                .name(name)
                .build();
    }
} 