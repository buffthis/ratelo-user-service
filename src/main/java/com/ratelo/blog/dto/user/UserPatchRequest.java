package com.ratelo.blog.dto.user;

import com.ratelo.blog.domain.user.UserType;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UserPatchRequest {
    private String username;
    private String name;
    private String bio;
    private Long profileImageId;
    private List<Long> careerIds;
    private List<Long> skillIds;
    private UserType userType;
    private Boolean disabled;
    private Boolean masked;
} 