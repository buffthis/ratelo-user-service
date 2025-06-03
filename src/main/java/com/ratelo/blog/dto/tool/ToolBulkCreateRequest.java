package com.ratelo.blog.dto.tool;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ToolBulkCreateRequest {
    private List<ToolCreateRequest> tools;
} 