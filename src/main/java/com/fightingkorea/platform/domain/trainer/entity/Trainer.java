package com.fightingkorea.platform.domain.trainer.entity;

import com.fightingkorea.platform.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trainerId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
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
    private Integer charge;

    @PrePersist
    public void prePersist() {
        charge = 15;
        automaticSettlement = false;
    }
}
