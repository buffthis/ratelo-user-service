package com.ratelo.blog.dto.tool;

import com.ratelo.blog.domain.tool.Tool;
import com.ratelo.blog.dto.image.ImageResponse;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolResponse {
    private Long id;
    private String name;
    private ImageResponse logo;

    public static ToolResponse from(Tool tool) {
        if (tool == null) return null;
        return ToolResponse.builder()
                .id(tool.getId())
                .name(tool.getName())
                .logo(ImageResponse.from(tool.getLogo()))
                .build();
    }

    public static List<ToolResponse> from(List<Tool> tools) {
        if (tools == null) return List.of();
        return tools.stream()
                .map(ToolResponse::from)
                .collect(Collectors.toList());
    }
}