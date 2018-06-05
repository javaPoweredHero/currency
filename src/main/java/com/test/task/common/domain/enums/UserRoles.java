package com.test.task.common.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum UserRoles {
    ADMIN("ADMIN"),
    USER("USER");

    private final String name;

    public static boolean contains(String role) {
        return Arrays.stream(UserRoles.values()).anyMatch(r -> r.name.equals(role));
    }

    public static UserRoles byRole(String role) {
        return Arrays.stream(UserRoles.values()).filter(r -> r.name.equals(role)).findFirst().orElse(null);
    }
}
