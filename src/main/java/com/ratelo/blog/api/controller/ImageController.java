package com.ratelo.blog.api.controller;

import com.ratelo.blog.api.dto.ImageResponse;
import com.ratelo.blog.domain.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{id}")
    public ImageResponse getImageById(@PathVariable Long id) {
        return ImageResponse.from(imageService.getImageById(id));
    }

    @GetMapping
    public List<ImageResponse> getAllImages() {
        return ImageResponse.from(imageService.getAllImages());
    }
}
