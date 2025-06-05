package com.ratelo.blog.domain.post;

import com.ratelo.blog.dto.post.PostPatchRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PostPatchMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePostFromDto(PostPatchRequest dto, @MappingTarget Post entity);
} 