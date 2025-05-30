package com.ratelo.blog.api.dto;

import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.domain.image.Image;
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

    public Company toEntity(Image logo) {
        return Company.builder()
                .name(name)
                .logo(logo)
                .build();
    }
} 