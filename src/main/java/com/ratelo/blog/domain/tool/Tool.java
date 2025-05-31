package com.ratelo.blog.domain.tool;

import com.ratelo.blog.domain.image.Image;
import com.ratelo.blog.dto.tool.ToolUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "logo_id")
    private Image logo;

    public void update(ToolUpdateRequest request, Image logo) {
        this.name = request.getName();
        this.setLogo(logo);
    }
}
