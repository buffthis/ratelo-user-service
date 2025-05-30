package com.ratelo.blog.domain.post;

import com.ratelo.blog.api.dto.PostCreateRequest;
import com.ratelo.blog.api.dto.PostUpdateRequest;
import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.image.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(PostCreateRequest request) {
        Image thumbnail = null;
        if (request.getThumbnailId() != null) {
            thumbnail = imageRepository.findById(request.getThumbnailId())
                .orElseThrow(() -> new EntityNotFoundException("Thumbnail image not found with id: " + request.getThumbnailId()));
        }
        return postRepository.save(request.toEntity(thumbnail));
    }

    public Post updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
        Image thumbnail = null;
        if (request.getThumbnailId() != null) {
            thumbnail = imageRepository.findById(request.getThumbnailId())
                .orElseThrow(() -> new EntityNotFoundException("Thumbnail image not found with id: " + request.getThumbnailId()));
        }
        post.update(request, thumbnail);
        return postRepository.save(post);
    }
} 