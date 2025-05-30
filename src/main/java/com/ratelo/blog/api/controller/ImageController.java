package com.ratelo.blog.api.controller;

import com.ratelo.blog.api.dto.ImageCreateRequest;
import com.ratelo.blog.api.dto.ImageResponse;
import com.ratelo.blog.api.dto.ImageUpdateRequest;
import com.ratelo.blog.domain.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ImageResponse createImage(@RequestBody ImageCreateRequest request) {
        return ImageResponse.from(imageService.createImage(request));
    }

    @PutMapping("/{id}")
    public ImageResponse updateImage(
            @PathVariable Long id,
            @RequestBody ImageUpdateRequest request) {
        return ImageResponse.from(imageService.updateImage(id, request));
    }
}
