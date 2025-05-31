package com.ratelo.blog.domain.project;

import com.ratelo.blog.api.dto.ProjectCreateRequest;
import com.ratelo.blog.api.dto.ProjectUpdateRequest;
import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.image.ImageRepository;
import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project createProject(ProjectCreateRequest request) {
        Image thumbnail = null;
        if (request.getThumbnailId() != null) {
            thumbnail = imageRepository.findById(request.getThumbnailId())
                .orElseThrow(() -> new EntityNotFoundException("Thumbnail image not found with id: " + request.getThumbnailId()));
        }
        Set<User> participants = new HashSet<>();
        if (request.getUserIds() != null) {
            for (Long userId : request.getUserIds()) {
                User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
                participants.add(user);
            }
        }
        Project project = request.toEntity();
        project.setThumbnail(thumbnail);
        project.setParticipants(participants);
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, ProjectUpdateRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));
        Image thumbnail = null;
        if (request.getThumbnailId() != null) {
            thumbnail = imageRepository.findById(request.getThumbnailId())
                .orElseThrow(() -> new EntityNotFoundException("Thumbnail image not found with id: " + request.getThumbnailId()));
        }
        project.update(request, thumbnail);
        List<User> participants = request.getUserIds() == null ? List.of() : userRepository.findAllById(request.getUserIds());
        project.setParticipants(new HashSet<>(participants));
        return projectRepository.save(project);
    }
} 