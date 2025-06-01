package com.ratelo.blog.domain.project;

import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.dto.project.ProjectUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
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

    @ManyToMany
    @JoinTable(
        name = "project_participant",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private Set<User> participants = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 추가 필드
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String periodNote;

    private String externalUrl;

    public void update(ProjectUpdateRequest request, Image thumbnail) {
        this.title = request.getTitle();
        this.subtitle = request.getSubtitle();
        this.content = request.getContent();
        this.setThumbnail(thumbnail);
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.periodNote = request.getPeriodNote();
        this.externalUrl = request.getExternalUrl();
    }

    public void addParticipant(User user) {
        this.participants.add(user);
        user.getProjects().add(this);
    }

    public void removeParticipant(User user) {
        this.participants.remove(user);
        user.getProjects().remove(this);
    }

    public void setParticipants(Set<User> users) {
        // 기존 관계 제거
        for (User user : new HashSet<>(this.participants)) {
            removeParticipant(user);
        }
        // 새 관계 추가
        if (users != null) {
            for (User user : users) {
                addParticipant(user);
            }
        }
    }
} 