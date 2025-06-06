package com.ratelo.blog.domain.company;

import com.ratelo.blog.dto.company.CompanyPatchRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CompanyPatchMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompanyFromDto(CompanyPatchRequest dto, @MappingTarget Company entity);
} 