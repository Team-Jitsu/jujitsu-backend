package com.fightingkorea.platform.domain.video.entity;

import com.fightingkorea.platform.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_videos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class UserVideo { // 동영상 구매 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_videos_id")
    private Long userVideoId; // 동영상 구매 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 유저 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video; // 동영상 아이디

    @Column (nullable = false)
    private Integer purchasePrice; // 구매 당시 가격

    @Column(nullable = false)
    private LocalDateTime purchasedAt; // 구매 날짜


    // 엔티티 저장 전 구입 시간 자동 설정
    @PrePersist
    public void prePersist() {
        this.purchasedAt = LocalDateTime.now();
    }

    public static UserVideo createUserVideo(User user, Video video, Integer purchasePrice){
        return UserVideo
                .builder()
                .user(user)
                .video(video)
                .purchasePrice(purchasePrice)
                .build();
    }


}
