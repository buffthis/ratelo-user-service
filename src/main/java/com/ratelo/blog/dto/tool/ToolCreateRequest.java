package com.ratelo.blog.dto.tool;

import com.ratelo.blog.domain.tool.Tool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolCreateRequest {
    @NotNull
    private String name;
    private Long logoId;

    public Tool toEntity() {
        return Tool.builder()
                .name(name)
                .build();
    }
} 