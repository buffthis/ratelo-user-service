package com.ratelo.blog.domain.career;

import com.ratelo.blog.domain.company.Company;
import com.ratelo.blog.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
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

    @Column(length = 64)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}