package com.ratelo.blog.domain.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class BannedUsernameValidator implements ConstraintValidator<BannedUsername, String> {
    private String[] bannedList;

    @Override
    public void initialize(BannedUsername constraintAnnotation) {
        this.bannedList = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return Arrays.stream(bannedList)
                .noneMatch(ban -> value.toLowerCase().startsWith(ban.toLowerCase()));
    }
} 