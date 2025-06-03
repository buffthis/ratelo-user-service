package com.ratelo.blog.dto.image;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ImageBulkCreateRequest {
    private List<ImageCreateRequest> images;
} 