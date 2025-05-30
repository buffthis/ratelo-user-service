package com.ratelo.blog.domain.image;

import com.ratelo.blog.api.dto.ImageCreateRequest;
import com.ratelo.blog.api.dto.ImageUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image createImage(ImageCreateRequest request) {
        return imageRepository.save(request.toEntity());
    }

    public Image updateImage(Long id, ImageUpdateRequest request) {
        Image image = imageRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Image not found with id: " + id));
        image.update(request);
        return imageRepository.save(image);
    }
}
