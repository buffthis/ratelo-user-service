package com.ratelo.blog.dto.image;

import com.ratelo.blog.domain.image.Image;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {
    private Long id;
    private String url;
    private String altText;

    public static ImageResponse from(Image image) {
        if (image == null) return null;
        return ImageResponse.builder()
                .id(image.getId())
                .url(image.getUrl())
                .altText(image.getAltText())
                .build();
    }

    public static List<ImageResponse> from(List<Image> images) {
        if (images == null) return List.of();
        return images.stream()
                .map(ImageResponse::from)
                .collect(Collectors.toList());
    }
}