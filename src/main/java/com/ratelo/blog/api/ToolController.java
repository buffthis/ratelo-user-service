package com.ratelo.blog.api;

import com.ratelo.blog.domain.tool.ToolService;
import com.ratelo.blog.dto.tool.ToolCreateRequest;
import com.ratelo.blog.dto.tool.ToolResponse;
import com.ratelo.blog.dto.tool.ToolUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tools")
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;

    @GetMapping("/{id}")
    public ToolResponse getToolById(@PathVariable Long id) {
        return ToolResponse.from(toolService.getToolById(id));
    }

    @GetMapping
    public List<ToolResponse> getAllTools() {
        return ToolResponse.from(toolService.getAllTools());
    }

    @PostMapping
    public ToolResponse createTool(@RequestBody ToolCreateRequest request) {
        return ToolResponse.from(toolService.createTool(request));
    }

    @PutMapping("/{id}")
    public ToolResponse updateTool(
            @PathVariable Long id,
            @RequestBody ToolUpdateRequest request) {
        return ToolResponse.from(toolService.updateTool(id, request));
    }
}
