package com.ratelo.blog.domain.skill;

import com.ratelo.blog.domain.tool.Tool;
import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.dto.skill.SkillUpdateRequest;
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

    @ManyToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;
    
    @Column(nullable = false)
    private byte level;

    @Column(length = 256)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(SkillUpdateRequest request, Tool tool) {
        this.setTool(tool);
        this.level = request.getLevel();
        this.description = request.getDescription();
    }

    public void setUser(User user) {
        this.user = user;
        user.getSkills().add(this);
    }

    public void removeUser(User user) {
        this.user = null;
        user.getSkills().remove(this);
    }
}
