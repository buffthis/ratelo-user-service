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

    @Column(nullable = true, unique = true)
    private String username;
    private String name;

    @OneToOne
    @JoinColumn(name = "logo_id")
    private Image logo;  // square logo

    @OneToOne
    @JoinColumn(name = "wide_logo_id")
    private Image wideLogo;

    public void update(CompanyUpdateRequest request, Image logo, Image wideLogo) {
        this.name = request.getName();
        this.setLogo(logo);
        this.setWideLogo(wideLogo);
    }
}
