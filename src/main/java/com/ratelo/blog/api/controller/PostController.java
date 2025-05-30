package com.ratelo.blog.api.controller;

import com.ratelo.blog.api.dto.PostCreateRequest;
import com.ratelo.blog.api.dto.PostResponse;
import com.ratelo.blog.api.dto.PostUpdateRequest;
import com.ratelo.blog.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable Long id) {
        return PostResponse.from(postService.getPostById(id));
    }

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return PostResponse.from(postService.getAllPosts());
    }

    @PostMapping
    public PostResponse createPost(@RequestBody PostCreateRequest request) {
        return PostResponse.from(postService.createPost(request));
    }

    @PutMapping("/{id}")
    public PostResponse updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request) {
        return PostResponse.from(postService.updatePost(id, request));
    }
} 