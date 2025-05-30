package com.ratelo.blog.domain.user;

import com.ratelo.blog.api.dto.UserUpdateRequest;
import com.ratelo.blog.domain.career.Career;
import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.post.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
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

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Career> careers = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    public void update(UserUpdateRequest request, Image profileImage, List<Career> careers) {
        this.username = request.getUsername();
        this.name = request.getName();
        this.bio = request.getBio();
        this.setProfileImage(profileImage);
        this.careers.clear();
        for (Career career : careers) {
            addCareer(career);
        }
    }

    public void addCareer(Career career) {
        this.careers.add(career);
        career.setUser(this);
    }
}