package com.ratelo.blog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequest {
    @NotNull
    private String title;
    private String subtitle;
    @NotNull
    private String content;
    private Long thumbnailId;
} 