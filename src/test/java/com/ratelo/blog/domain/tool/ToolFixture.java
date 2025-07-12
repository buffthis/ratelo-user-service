package com.ratelo.blog.domain.tool;

import com.ratelo.blog.domain.image.ImageFixture;

public class ToolFixture {

    public static final Long ID = 1L;
    public static final String NAME = "JUnit";

    public static Tool.ToolBuilder builder() {
        return Tool.builder()
            .id(ID)
            .name(NAME)
            .logo(ImageFixture.createDefaultImage());
    }

    public static Tool createDefaultTool() {
        return builder().build();
    }
} 