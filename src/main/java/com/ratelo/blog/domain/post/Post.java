package com.ratelo.blog.domain.post;

import com.ratelo.blog.api.dto.PostUpdateRequest;
import com.ratelo.blog.domain.image.Image;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 100)
    private String subtitle;

    @Lob
    @Column(nullable = false)
    private String content;

    @OneToOne
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnail;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public void update(PostUpdateRequest request, Image thumbnail) {
        this.title = request.getTitle();
        this.subtitle = request.getSubtitle();
        this.content = request.getContent();
        this.thumbnail = thumbnail;
    }
}
