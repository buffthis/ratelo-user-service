package com.ratelo.blog.domain.skill;

import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.api.dto.SkillUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

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

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public void update(SkillUpdateRequest request, Image logo) {
        this.name = request.getName();
        this.level = request.getLevel();
        this.description = request.getDescription();
        this.setLogo(logo);
    }
}
