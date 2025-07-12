package com.ratelo.blog.domain.image;

public class ImageFixture {

    public static final Long ID = 1L;
    public static final String URL = "https://example.com/image.png";
    public static final String ALT_TEXT = "image";

    public static Image.ImageBuilder builder() {
        return Image.builder()
            .id(ID)
            .url(URL)
            .altText(ALT_TEXT);
    }

    public static Image createDefaultImage() {
        return builder().build();
    }
} 