package com.ratelo.blog.dto.image;

import com.ratelo.blog.domain.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageCreateRequest {
    @NotNull
    private String url;
    private String altText;

    public Image toEntity() {
        return Image.builder()
                .url(url)
                .altText(altText)
                .build();
    }
} 