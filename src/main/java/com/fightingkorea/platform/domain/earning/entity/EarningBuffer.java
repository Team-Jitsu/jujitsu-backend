package com.fightingkorea.platform.domain.earning.entity;

import com.fightingkorea.platform.domain.video.entity.UserVideo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "earning_buffer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EarningBuffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buffer_id")
    private Long bufferId; // 정산 버퍼 아이디

    @Column
    private Long trainerId; // 선수 아이디

    @ManyToOne
    @JoinColumn(name = "user_videos_id")
    private UserVideo userVideo; // 동영상 구매 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earning_id")
    private Earning earning; // 정산 내역 아이디

    @Column
    private Integer amount; // 수익

    @Column
    private LocalDateTime createdAt; // 생성 일자

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    public void update(Long trainerId, Integer amount){
        this.trainerId = trainerId;
        this.amount = amount;
    }

    public static EarningBuffer createEarningBuffer(Long trainerId, UserVideo userVideo, Integer amount) {
        return EarningBuffer
                .builder()
                .trainerId(trainerId)
                .userVideo(userVideo)
                .amount(amount)
                .build();
    }

}
