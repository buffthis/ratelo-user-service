package com.ratelo.blog.domain.career;

import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.domain.user.User;
import com.ratelo.blog.dto.career.CareerUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
    indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_start_date", columnList = "startDate"),
        @Index(name = "idx_user_id_start_date", columnList = "user_id, startDate")
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String team;

    private String position;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length = 12)
    private String periodNote;

    @Column(length = 64)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Builder.Default
    private boolean hidden = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean masked = false;

    public void update(CareerUpdateRequest request, Company company) {
        this.setCompany(company);
        this.team = request.getTeam();
        this.position = request.getPosition();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.periodNote = request.getPeriodNote();
        this.description = request.getDescription();
        this.hidden = request.getHidden();
        this.masked = request.getMasked();
    }

    public void setUser(User user) {
        this.user = user;
        user.getCareers().add(this);
    }

    public void removeUser(User user) {
        this.user = null;
        user.getCareers().remove(this);
    }
}