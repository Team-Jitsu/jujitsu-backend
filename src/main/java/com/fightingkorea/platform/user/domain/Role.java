package com.fightingkorea.platform.user.domain;

import lombok.Getter;

@Getter
public enum Role {
    TRAINEE("수련생"),
    PLAYER("선수"),
    ADMIN("관리자");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public static Role fromLabel(String label) {
        for (Role role : values()) {
            if (role.label.equals(label)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role label: " + label);
    }

}