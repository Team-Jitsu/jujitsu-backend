package com.fightingkorea.platform.domain.auth.entity;

import com.fightingkorea.platform.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * 토큰을 DB에 저장
 * 사용자별로 토큰을 저장/검증
 */
@Entity
@Table(name = "refresh_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public final class RefreshToken {

    @Id
    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
