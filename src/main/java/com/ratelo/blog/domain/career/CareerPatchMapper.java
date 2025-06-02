package com.ratelo.blog.domain.career;

import com.ratelo.blog.dto.career.CareerPatchRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CareerPatchMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCareerFromDto(CareerPatchRequest dto, @MappingTarget Career entity);
} 