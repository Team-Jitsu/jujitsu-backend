package com.fightingkorea.platform.domain.auth.entity;

import com.fightingkorea.platform.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * 토큰을 DB에 저장
 * 사용자별로 토큰을 저장/검증
 */
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public final class RefreshToken {

    @Id
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
