package com.ratelo.blog.api;

import com.ratelo.blog.domain.image.ImageService;
import com.ratelo.blog.dto.image.ImageCreateRequest;
import com.ratelo.blog.dto.image.ImageResponse;
import com.ratelo.blog.dto.image.ImageUpdateRequest;
import com.ratelo.blog.dto.image.ImageBulkCreateRequest;
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

    @PostMapping("/bulk")
    public List<ImageResponse> createImages(@RequestBody ImageBulkCreateRequest request) {
        return ImageResponse.from(imageService.createImages(request.getImages()));
    }

    @PutMapping("/{id}")
    public ImageResponse updateImage(
            @PathVariable Long id,
            @RequestBody ImageUpdateRequest request) {
        return ImageResponse.from(imageService.updateImage(id, request));
    }
}
