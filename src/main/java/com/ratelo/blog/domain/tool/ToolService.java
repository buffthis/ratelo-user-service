package com.ratelo.blog.domain.tool;

import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.image.ImageRepository;
import com.ratelo.blog.dto.tool.ToolCreateRequest;
import com.ratelo.blog.dto.tool.ToolUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolRepository toolRepository;
    private final ImageRepository imageRepository;

    public Tool getToolById(Long id) {
        return toolRepository.findById(id).orElse(null);
    }

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public Tool createTool(ToolCreateRequest request) {
        Tool tool = request.toEntity();

        Image logo = imageRepository.findById(request.getLogoId())
            .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
        tool.setLogo(logo);

        return toolRepository.save(tool);
    }

    public Tool updateTool(Long id, ToolUpdateRequest request) {
        Tool tool = toolRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tool not found with id: " + id));
        Image logo = imageRepository.findById(request.getLogoId())
            .orElseThrow(() -> new EntityNotFoundException("Logo image not found with id: " + request.getLogoId()));
        tool.update(request, logo);
        return toolRepository.save(tool);
    }
}
