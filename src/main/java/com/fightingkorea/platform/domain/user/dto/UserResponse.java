package com.fightingkorea.platform.domain.user.dto;

import com.fightingkorea.platform.domain.user.entity.type.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class UserResponse {

    /**
     * 아이디
     */
    private long userId;

    /**
     * 닉네임
     */
    private String nickname;

    /**
     * 권한
     */
    private Role role;

    /**
     * 생성 시간
     */
    private LocalDateTime createdAt;
}
