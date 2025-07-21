package com.fightingkorea.platform.domain.earning.entity;

import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "earnings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Earning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "earning_id", nullable = false)
    private Long earningId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Column(name = "is_settled", nullable = false)
    private boolean isSettled;

    @Column(name = "request_settlement", nullable = false)
    private boolean requestSettlement;

    @Column(name = "complete_settlement_at")
    private LocalDateTime completeSettlementAt;

    @Column(name = "request_settlement_at")
    private LocalDateTime requestSettlementAt;
}
