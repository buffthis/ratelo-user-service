package com.ratelo.blog.dto.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageUpdateRequest {
    @NotNull
    private String url;
    private String altText;
} 
