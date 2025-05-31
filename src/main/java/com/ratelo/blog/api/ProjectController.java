package com.ratelo.blog.api;

import com.ratelo.blog.domain.project.ProjectService;
import com.ratelo.blog.dto.project.ProjectCreateRequest;
import com.ratelo.blog.dto.project.ProjectResponse;
import com.ratelo.blog.dto.project.ProjectUpdateRequest;
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