package com.ratelo.blog.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserBulkCreateRequest {
    private List<UserCreateRequest> users;
}
