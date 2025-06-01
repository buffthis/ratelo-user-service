package com.ratelo.blog.dto.project;

import com.ratelo.blog.domain.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateRequest {
    @NotNull
    private String title;
    private String subtitle;
    @NotNull
    private String content;
    private Long thumbnailId;
    private List<Long> userIds;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String periodNote;
    private String externalUrl;

    public Project toEntity() {
        return Project.builder()
                .title(title)
                .subtitle(subtitle)
                .content(content)
                .createdAt(LocalDateTime.now())
                .startDate(startDate)
                .endDate(endDate)
                .periodNote(periodNote)
                .build();
    }
} 