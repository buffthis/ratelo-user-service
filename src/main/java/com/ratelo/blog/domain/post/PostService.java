package com.ratelo.blog.domain.post;

import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.image.ImageRepository;
import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.domain.user.UserRepository;
import com.ratelo.blog.dto.post.PostCreateRequest;
import com.ratelo.blog.dto.post.PostUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

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
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));
        Post post = request.toEntity();
        post.setThumbnail(thumbnail);
        post.setUser(user);
        return postRepository.save(post);
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