package com.ratelo.blog.api.controller;

import com.ratelo.blog.api.dto.ProjectCreateRequest;
import com.ratelo.blog.api.dto.ProjectResponse;
import com.ratelo.blog.api.dto.ProjectUpdateRequest;
import com.ratelo.blog.domain.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable Long id) {
        return ProjectResponse.from(projectService.getProjectById(id));
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects() {
        return ProjectResponse.from(projectService.getAllProjects());
    }

    @PostMapping
    public ProjectResponse createProject(@RequestBody ProjectCreateRequest request) {
        return ProjectResponse.from(projectService.createProject(request));
    }

    @PutMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @RequestBody ProjectUpdateRequest request) {
        return ProjectResponse.from(projectService.updateProject(id, request));
    }
} 