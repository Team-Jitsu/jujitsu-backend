package com.fightingkorea.platform.domain.trainer.repository.impl;

import com.fightingkorea.platform.domain.trainer.dto.TrainerResponse;
import com.fightingkorea.platform.domain.trainer.entity.QTrainer;
import com.fightingkorea.platform.domain.trainer.repository.CustomTrainerRepository;
import com.fightingkorea.platform.domain.user.dto.UserResponse;
import com.fightingkorea.platform.domain.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomTrainerRepositoryImpl implements CustomTrainerRepository {

    private final JPAQueryFactory queryFactory;

    QTrainer trainer = QTrainer.trainer;
    QUser user = QUser.user;

    @Override
    public PageImpl<TrainerResponse> findBySomeCondition(Pageable pageable) {
        List<TrainerResponse> contents = queryFactory
                .select(Projections.constructor(TrainerResponse.class,
                        trainer.trainerId,
                        trainer.accountOwnerName,
                        trainer.accountNumber,
                        trainer.bio,
                        trainer.automaticSettlement,
                        trainer.charge,
                        Projections.constructor(UserResponse.class,
                                trainer.user.userId,
                                trainer.user.nickname,
                                trainer.user.role,
                                trainer.user.createdAt
                        )
                ))
                .from(trainer)
                .join(trainer.user, user)
                .where(trainer.user.isActive.eq(true))
                .orderBy(trainer.user.createdAt.desc())
                .offset((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(trainer.count())
                .from(trainer)
                .where(trainer.user.isActive.eq(true))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

}
