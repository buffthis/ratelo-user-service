package com.ratelo.blog.domain.career;

import com.ratelo.blog.api.dto.CareerUpdateRequest;
import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
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

    public void update(CareerUpdateRequest request, Company company, User user) {
        this.company = company;
        this.team = request.getTeam();
        this.position = request.getPosition();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.periodNote = request.getPeriodNote();
        this.description = request.getDescription();
    }

    public void setUser(User user) {
        this.user = user;
        user.getCareers().add(this);
    }
}