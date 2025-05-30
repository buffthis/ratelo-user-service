package com.ratelo.blog.domain.skill;

import com.ratelo.blog.api.dto.SkillUpdateRequest;
import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 16)
    private String name;

    @OneToOne
    @JoinColumn(name = "logo_id")
    private Image logo;
    
    @Column(nullable = false)
    private byte level;

    @Column(length = 256)
    private String description;

    @ManyToMany(mappedBy = "skills")
    @Builder.Default
    private Set<User> users = new HashSet<>();

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public void update(SkillUpdateRequest request, Image logo) {
        this.name = request.getName();
        this.level = request.getLevel();
        this.description = request.getDescription();
        this.setLogo(logo);
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getSkills().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getSkills().remove(this);
    }
}
