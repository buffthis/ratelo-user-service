package com.ratelo.blog.dto.tool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolUpdateRequest {
    @NotNull
    private String name;
    private Long logoId;
} 