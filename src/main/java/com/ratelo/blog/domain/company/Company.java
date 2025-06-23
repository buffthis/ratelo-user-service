package com.ratelo.blog.domain.company;

import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.organization.Organization;
import com.ratelo.blog.dto.company.CompanyUpdateRequest;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Company extends Organization {

    public void update(CompanyUpdateRequest request, Image logo, Image wideLogo) {
        setName(request.getName());
        setLogo(logo);
        setWideLogo(wideLogo);
    }
}
