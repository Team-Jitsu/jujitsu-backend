package com.fightingkorea.platform.domain.earning.entity;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "earnings")
@Builder
@Getter
public class Earning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "earning_id")
    private Long earningId; // 정산 내역 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer; // 선수 아이디

    @Column
    private Long totalAmount; // 수익 합계

    @Column
    private Boolean isSettled; // 정산 여부

    @Column
    private Boolean requestSettlement; // 정산 요청

    @Column
    private LocalDateTime completeSettlementAt;  // 정산 완료 시간

    @Column
    private LocalDateTime requestSettlementAt; // 정산 요청 시간

    // 엔티티 저장 전 업로드 시간, 정산여부, 정산 요청 시간 자동 설정
    @PrePersist
    public void prePersist(){

        if (this.isSettled == null){
            this.isSettled = false;
        }
        if(this.requestSettlement == null){
            this.requestSettlement = true;
        }

        this.requestSettlementAt = LocalDateTime.now();

    }

    // dto의 trainerId로 조회한 Trainer 객체를 파라미터에 주입
    public static Earning createEarning(CreateEarningRequest req, Trainer trainer){
        return Earning
                .builder()
                .trainer(trainer)
                .totalAmount(req.getTotalAmount())
                .build();

    }

}
