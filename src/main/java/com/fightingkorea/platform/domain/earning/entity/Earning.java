package com.fightingkorea.platform.domain.earning.entity;

import com.fightingkorea.platform.domain.earning.dto.CreateEarningRequest;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.video.entity.UserVideo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "earnings")
@Builder
@Getter
@Setter
public class Earning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "earning_id")
    private Long earningId; // 정산 내역 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer; // 선수 아이디

    @OneToMany(mappedBy = "earning")
    private List<EarningBuffer> earningBuffers = new ArrayList<>(); // 정산 버퍼 리스트

    @Column
    private Long totalAmount; // 수익 합계

    @Column(nullable = false)
    private Boolean isSettled; // 정산 여부

    @Column
    private Boolean requestSettlement; // 정산 요청

    @Column
    private LocalDateTime completeSettlementAt;  // 정산 완료 시간

    @Column
    private LocalDateTime requestSettlementAt; // 정산 요청 시간: 초기값은 null, 정산완료시 LocalDateTime.now();

    // 엔티티 저장 전 업로드 시간, 정산여부, 정산 요청 시간 자동 설정
    @PrePersist
    public void prePersist() {

        if (this.isSettled == null){
            this.isSettled = false; // 정산 여부를 false를 기본값으로. 정산 완료후 true로 변환
        }
        if(this.requestSettlement == null){
            this.requestSettlement = true; // 초기값 true. 필요시 수동변경(??)언제?
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