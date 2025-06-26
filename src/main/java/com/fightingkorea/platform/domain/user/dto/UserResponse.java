package com.fightingkorea.platform.domain.user.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UserResponse {
    private Long userId;

    private String nickname;

    private String role;

    private LocalDateTime createdAt;
}
