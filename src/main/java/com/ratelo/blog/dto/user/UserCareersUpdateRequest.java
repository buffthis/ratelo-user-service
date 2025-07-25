package com.ratelo.blog.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCareersUpdateRequest {
    private List<Long> careerIds;
} 