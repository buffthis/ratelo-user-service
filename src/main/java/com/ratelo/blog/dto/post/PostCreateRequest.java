package com.ratelo.blog.dto.post;

import com.ratelo.blog.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
    @NotNull
    private String title;
    private String subtitle;
    @NotNull
    private String content;
    private Long thumbnailId;
    private Long userId;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .subtitle(subtitle)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
    }
} 