package com.ratelo.blog.domain.user;

import com.ratelo.blog.api.dto.UserUpdateRequest;
import com.ratelo.blog.domain.career.Career;
import com.ratelo.blog.domain.image.Image;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;

    @Column(length = 64)
    private String bio;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Career> careers = new ArrayList<>();

    public void update(UserUpdateRequest request, Image profileImage, List<Career> careers) {
        this.username = request.getUsername();
        this.name = request.getName();
        this.bio = request.getBio();
        this.profileImage = profileImage;
        this.careers = careers;
    }
}