package com.ratelo.blog.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPatchRequest {
    private String title;
    private String subtitle;
    private String content;
    private Long thumbnailId;
    private String externalUrl;
    private Boolean hidden;
    private Long userId;
} 