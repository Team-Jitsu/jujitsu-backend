package com.fightingkorea.platform.user.domain;

import lombok.Getter;

@Getter
public enum Sex {
    MALE("남자"),
    FEMALE("여자");

    private final String label;

    Sex(String label) {
        this.label = label;
    }

    public static Sex fromLabel(String label) {
        for (Sex sex : values()) {
            if (sex.label.equals(label)) {
                return sex;
            }
        }
        throw new IllegalArgumentException("Unknown sex label: " + label);
    }

}