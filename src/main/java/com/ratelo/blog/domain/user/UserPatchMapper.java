package com.ratelo.blog.domain.user;

import com.ratelo.blog.dto.user.UserPatchRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserPatchMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserPatchRequest dto, @MappingTarget User entity);
} 