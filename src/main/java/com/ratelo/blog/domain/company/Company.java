package com.ratelo.blog.domain.company;

import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.dto.company.CompanyUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "logo_id")
    private Image logo;

    @Column(nullable = false)
    @Builder.Default
    private boolean isHidden = false;

    public void update(CompanyUpdateRequest request, Image logo) {
        this.name = request.getName();
        this.setLogo(logo);
    }
}
