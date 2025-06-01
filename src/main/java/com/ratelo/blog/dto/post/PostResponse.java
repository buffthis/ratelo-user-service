package com.ratelo.blog.dto.post;

import com.ratelo.blog.domain.post.Post;
import com.ratelo.blog.dto.image.ImageResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String subtitle;
    private String content;
    private ImageResponse thumbnail;
    private LocalDateTime createdAt;
    private String externalUrl;

    public static PostResponse from(Post post) {
        if (post == null) return null;
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .subtitle(post.getSubtitle())
                .content(post.getContent())
                .thumbnail(ImageResponse.from(post.getThumbnail()))
                .createdAt(post.getCreatedAt())
                .externalUrl(post.getExternalUrl())
                .build();
    }

    public static List<PostResponse> from(List<Post> posts) {
        if (posts == null) return List.of();
        return posts.stream()
                .map(PostResponse::from)
                .toList();
    }
}
