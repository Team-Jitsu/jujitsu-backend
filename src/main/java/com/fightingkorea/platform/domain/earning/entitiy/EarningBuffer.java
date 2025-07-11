package com.fightingkorea.platform.domain.earning.entitiy;

import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.video.entity.UserVideo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "earning_buffer")
@Getter
@Setter
public class EarningBuffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buffer_id", nullable = false)
    private Long bufferId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_videos_id", nullable = false)
    private UserVideo userVideo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "earning_id")
    private Earning earning;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
