package com.fightingkorea.platform.domain.earning.repository.impl;

import com.fightingkorea.platform.domain.earning.entity.Earning;
import com.fightingkorea.platform.domain.earning.entity.QEarningBuffer;
import com.fightingkorea.platform.domain.earning.repository.EarningRepository;
import com.fightingkorea.platform.domain.trainer.entity.Trainer;
import com.fightingkorea.platform.domain.trainer.repository.TrainerRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomEarningRepository {

    private final JPAQueryFactory queryFactory;
    private final EarningRepository earningRepository;
    private final TrainerRepository trainerRepository;

    public Earning createEarningAndAssignToBuffers(Long trainerId) {
        QEarningBuffer eb = QEarningBuffer.earningBuffer;

        // 1. amount 총합 조회
        Long totalAmount = queryFactory
                .select(eb.amount.sum().longValue())
                .from(eb)
                .where(eb.trainer.trainerId.eq(trainerId)
                        .and(eb.earning.isNull()))
                .fetchOne();

        if (totalAmount == null || totalAmount == 0) {
            throw new IllegalStateException("정산할 항목이 없습니다.");
        }

        Trainer trainer = trainerRepository.getReferenceById(trainerId);

        // 2. Earning 생성 및 저장
        Earning earning = Earning.builder()
                .trainer(trainer)
                .totalAmount(totalAmount)
                .isSettled(false)
                .requestSettlement(false)
                .build();

        earningRepository.save(earning);

        // 3. earning_id를 earning_buffer에 일괄 update
        queryFactory
                .update(eb)
                .set(eb.earning, earning)
                .where(eb.trainer.trainerId.eq(trainerId)
                        .and(eb.earning.isNull()))
                .execute();

        return earning;
    }

}
