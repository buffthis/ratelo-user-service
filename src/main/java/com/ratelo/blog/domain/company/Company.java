package com.ratelo.blog.domain.company;

import com.ratelo.blog.api.dto.CompanyUpdateRequest;
import com.ratelo.blog.domain.image.Image;
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

    @ManyToOne
    @JoinColumn(name = "logo_id")
    private Image logo;

    public void update(CompanyUpdateRequest request, Image logo) {
        this.name = request.getName();
        this.logo = logo;
    }
}
