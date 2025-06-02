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
public class PostSummaryResponse {
    private Long id;
    private String title;
    private String subtitle;
    private ImageResponse thumbnail;
    private LocalDateTime createdAt;
    private String externalUrl;
    private Boolean hidden;

    public static PostSummaryResponse from(Post post) {
        if (post == null) return null;
        return PostSummaryResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .subtitle(post.getSubtitle())
                .thumbnail(ImageResponse.from(post.getThumbnail()))
                .createdAt(post.getCreatedAt())
                .externalUrl(post.getExternalUrl())
                .hidden(post.isHidden())
                .build();
    }

    public static List<PostSummaryResponse> from(List<Post> posts) {
        if (posts == null) return List.of();
        return posts.stream()
                .map(PostSummaryResponse::from)
                .toList();
    }
} 