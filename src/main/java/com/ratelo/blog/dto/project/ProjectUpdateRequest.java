package com.ratelo.blog.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateRequest {
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

} 