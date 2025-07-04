package com.fightingkorea.platform.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {

    /**
     * 아이디
     */
    private Long userId;

    /**
     * 닉네임
     */
    private String nickname;

    /**
     * 권한
     */
    private String role;

    /**
     * 생성 시간
     */
    private LocalDateTime createdAt;
}
