package com.ratelo.blog.dto.user;

import com.ratelo.blog.domain.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    @NotNull
    private String username;
    @NotNull
    private String name;
    private String bio;
    private Long profileImageId;
    private List<Long> careerIds = new ArrayList<>();
    private List<Long> skillIds = new ArrayList<>();
    private UserType userType = UserType.MEMBER;
} 