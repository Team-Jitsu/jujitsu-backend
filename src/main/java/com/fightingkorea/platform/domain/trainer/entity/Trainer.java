package com.fightingkorea.platform.domain.trainer.entity;

import com.fightingkorea.platform.domain.trainer.dto.TrainerUpdateRequest;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.video.entity.Video;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long trainerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id", // 명시적 참조 컬럼 지정
            foreignKey = @ForeignKey(name = "trainers_ibfk_1") // FK 이름 지정
    )
    private User user;

    @Column(length = 100, nullable = false)
    private String accountOwnerName;

    @Column(length = 100, nullable = false)
    private String accountNumber;

    @Column(columnDefinition = "text")
    private String bio;

    @Column(columnDefinition = "tinyint(1)", nullable = false)
    private Boolean automaticSettlement;

    @Column(nullable = false)
    private Integer charge; // 수수료

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Video> videos = new ArrayList<>(); // 선수가 보유한 비디오 리스트

    public static Trainer createTrainer(User user, String accountOwnerName, String accountNumber, String bio, Boolean automaticSettlement) {
        return Trainer.builder()
                .user(user)
                .accountOwnerName(accountOwnerName)
                .accountNumber(accountNumber)
                .bio(bio)
                .automaticSettlement(automaticSettlement)
                .build();
    }

    // 트레이너 정보 업데이트
    public void updateInfo(TrainerUpdateRequest trainerUpdateRequest) {
        this.accountOwnerName = trainerUpdateRequest.getAccountOwnerName();
        this.accountNumber = trainerUpdateRequest.getAccountNumber();
        this.bio = trainerUpdateRequest.getBio();
        this.automaticSettlement = trainerUpdateRequest.getAutomaticSettlement();
    }

    // 엔티티 저장 전 수수료 기본값 15로 지정
    @PrePersist
    public void prePersist() {
        charge = 15;
    }
}
