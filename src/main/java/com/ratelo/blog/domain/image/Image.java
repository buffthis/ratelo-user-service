package com.ratelo.blog.domain.image;

import com.ratelo.blog.api.dto.ImageUpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String altText;

    public void update(ImageUpdateRequest request) {
        this.url = request.getUrl();
        this.altText = request.getAltText();
    }
}
