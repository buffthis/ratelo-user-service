package com.ratelo.blog.domain.organization;

import com.ratelo.blog.domain.image.Image;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
public class Organization {

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
}
