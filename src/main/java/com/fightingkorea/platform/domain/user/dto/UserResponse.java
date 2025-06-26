package com.fightingkorea.platform.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;

    private String nickname;

    private String role;

    private LocalDateTime createdAt;
}
