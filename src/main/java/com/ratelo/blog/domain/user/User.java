package com.ratelo.blog.domain.user;

import com.ratelo.blog.domain.career.Career;
import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.post.Post;
import com.ratelo.blog.domain.project.Project;
import com.ratelo.blog.domain.skill.Skill;
import com.ratelo.blog.dto.user.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @BannedUsername(value = {"user", "admin", "root", "system", "unblind"})
    private String username;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;

    @Column(length = 64)
    private String bio;

    @Column(nullable = false, length = 100)
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Career> careers = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "user_skill",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Builder.Default
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany(mappedBy = "participants")
    @Builder.Default
    private Set<Project> projects = new HashSet<>();

    private boolean disabled = false;
    private boolean masked = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserType userType = UserType.MEMBER;

    public void update(UserUpdateRequest request, Image profileImage, List<Career> careers, List<Skill> skills) {
        this.username = request.getUsername();
        this.name = request.getName();
        this.bio = request.getBio();
        this.setProfileImage(profileImage);
        this.setCareers(careers);
        this.setSkills(skills);
        this.userType = request.getUserType();
    }

    public void addCareer(Career career) {
        this.careers.add(career);
        career.setUser(this);
    }

    public void removeCareer(Career career) {
        this.careers.remove(career);
        career.setUser(null);
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
        skill.setUser(this);
    }

    public void removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.setUser(null);
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public void setCareers(List<Career> careers) {
        this.careers.clear();
        for (Career career : careers) {
            addCareer(career);
        }
    }

    public void setSkills(List<Skill> skills) {
        this.skills.clear();
        for (Skill skill : skills) {
            addSkill(skill);
        }
    }

    public void addProject(Project project) {
        this.projects.add(project);
        project.getParticipants().add(this);
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
        project.getParticipants().remove(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}